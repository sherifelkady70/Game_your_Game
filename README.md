# Game your Game

A minimal Android app that browses game genres and games using the [RAWG API](https://rawg.io/apidocs), with local caching and a clean modular architecture.

---

## Tech Stack

Each layer and the main libraries used:

| Category | Technology | Role |
|----------|------------|------|
| **Language & runtime** | Kotlin 2.0 | App language; coroutines for async. |
| **UI** | Jetpack Compose | Declarative UI; single activity. |
| **UI** | Material 3 | Theming and components. |
| **Navigation** | Navigation Compose | Screen navigation and arguments (`genreId`, `gameId`). |
| **Dependency injection** | Hilt (Dagger) | DI for ViewModels, repositories, API, DB, mappers. |
| **Dependency injection** | KSP | Annotation processing for Hilt and Room. |
| **Networking** | Retrofit 2 | REST API client for RAWG. |
| **Networking** | OkHttp | HTTP client (used by Retrofit). |
| **Networking** | Gson | JSON (de)serialization via Retrofit converter. |
| **Local storage** | Room | SQLite for caching genres, games, and game details. |
| **Async / streams** | Kotlin Coroutines | Background work and structured concurrency. |
| **Async / streams** | Kotlin Flow | Reactive streams for loading state (Loading / Success / Empty / Error). |
| **Images** | Coil (Compose) | Image loading in Compose. |
| **Testing** | JUnit 4 | Unit test runner. |
| **Testing** | MockK | Mocking for Kotlin (repositories, APIs, DAOs). |
| **Testing** | kotlinx-coroutines-test | `runTest`, test dispatcher for Flow/coroutines. |

**Build & SDK**

- **Min SDK:** 24  
- **Target / Compile SDK:** 36  
- **Build:** Gradle 8.x, Android Gradle Plugin 8.12  
- **Version catalog:** `gradle/libs.versions.toml` for dependency versions.

---

## Architecture

### Architecture choices 

- **Feature modules** – Clear boundaries (genres, games, game-details), easier to test and scale; features depend only on `core`, not on each other.
- **Clean-style layers per feature** – Presentation (UI + ViewModel) → Domain (models, repository interface, use cases) → Data (API, Room, mappers, repository impl). Domain stays free of frameworks and is easy to unit test.
- **MVI-style presentation** – User intents, single state (`StateFlow`), and one-off effects (e.g. navigation) keep UI logic predictable and testable.
- **Offline-first via shared helper** – One `apiCallWithHandlingOffline` in `core` drives all list/details loading: emit Loading → optional Success(cache) → API → mapAndSave → Success/Empty/Error. Reduces duplication and keeps cache/network behavior consistent.
- **Repository over data sources** – Single entry point for each feature; use cases depend on repository interfaces only, so they can be tested with mocks.

### Modular structure

- **`app`** – Application entry, `MainActivity`, navigation host (`AppNavHost`), Hilt modules (API key, `BASE_URL`, `AppDatabase`), and shared `AppDatabase` (Room).
- **`core`** – Shared utilities and configuration: `NetworkStateResource`, `apiCallWithHandlingOffline`, `Constants` (e.g. `PAGE_SIZE`, `LOAD_MORE_THRESHOLD`, error strings), theme (Color, Theme, Type), navigation routes (`NavRoutes`), and core DI (`BASE_URL`, `RawgApiKey`, Retrofit, OkHttp).
- **`feature/genres`** – Genre list screen and flow.
- **`feature/games`** – Games list by genre with pagination.
- **`feature/game-details`** – Single game details screen.

Features depend only on `core` (no cross-feature dependencies).

### Per-feature layers

1. **Presentation**  
   - **Screen:** Compose UI.  
   - **ViewModel:** Hilt-injected; exposes a single `StateFlow` and handles user intents.  
   - **Intent / State / Effect:** MVI-style (user actions → state updates → optional effects like navigation).

2. **Domain**  
   - **Model:** Plain Kotlin data classes (no DTO/Room types).  
   - **Repository:** Interface only; implemented in data.  
   - **Use case:** Single responsibility; calls repository and may transform/sort (e.g. genres sorted by id).

3. **Data**  
   - **Remote:** Retrofit API interface and DTOs.  
   - **Local:** Room DAO and entities.  
   - **Mapper:** DTO ↔ Entity ↔ Domain.  
   - **Repository implementation:** Uses `apiCallWithHandlingOffline` from core.

### Offline-first flow (core)

`apiCallWithHandlingOffline(readCache, apiCall, mapAndSave, emptyCheck)`:

1. Emit `Loading`.
2. If `readCache()` returns non-null, emit `Success(cached)`.
3. Run `apiCall()`, then `mapAndSave(response)` (e.g. persist and return domain data).
4. If `emptyCheck(result)` is true, emit `Empty`; else emit `Success(result)`.
5. On exception, emit `Error(throwable)`.

So the app always shows loading, optionally cached data, then network result or empty/error.

### Dependency flow

- **App** → `core`, `feature:genres`, `feature:games`, `feature:game-details`.  
- **Feature modules** → `core` only.  
- **Core** → No feature modules; only AndroidX, OkHttp/Retrofit, Hilt.

---

## Assumptions & Shortcuts

1. **API key and base URL**  
   - RAWG API key and `BASE_URL` come from `local.properties` (or project properties) and are exposed via `BuildConfig`.  
   - If the key is missing or blank, requests are still sent (without a `key` query param); RAWG will typically reject them.

2. **Local cache**  
   - Room is used as a cache for genres, games per genre, and game details.  
   - No formal sync or conflict resolution; repositories overwrite/insert as in `mapAndSave` (e.g. games for a genre are cleared when loading page 1 via `deleteByGenre`).

3. **Error handling**  
   - All API/local failures are surfaced as `NetworkStateResource.Error(throwable)`.  
   - UI uses generic strings from `Constants` (e.g. `GENERAL_ERROR_MESSAGE`, `EMPTY_MESSAGE`) rather than fine-grained error types or server messages.

4. **Game details rating**  
   - Rating is displayed as a formatted string (e.g. `"4.5"`).  
   - The mapper uses `String.format(Locale.US, "%.1f", dto.rating)`; null `rating` from the API is not handled and can cause issues.

5. **Pagination**  
   - Games list uses `page` and `page_size` (e.g. `Constants.PAGE_SIZE = 10`).  
   - “Load more” is driven by `Constants.LOAD_MORE_THRESHOLD`.  
   - Only page 1 triggers a full refresh of cached games for that genre (`deleteByGenre(genreId)` when `page == 1`).

6. **Testing**  
   - Unit tests cover **mappers**, **use cases**, and **repository implementations** (MockK, `runTest`).  
   - No UI or instrumentation tests are included; the project is set up to add them later.

7. **Navigation**  
   - Routes and arguments (`genreId`, `gameId`) are in `core` (`NavRoutes`); the app `NavHost` composes feature screens and passes arguments.  
   - No deep links or special back-stack handling.

8. **Constants**  
   - API path segments (`GENRES`, `GAMES`), `PAGE_SIZE`, `LOAD_MORE_THRESHOLD`, and UI strings (empty/error, retry, labels) live in `core.utilits.Constants`.

---

## Running the app

1. In the project root, create or edit `local.properties` and add:
   - `RAWG_API_KEY=your_rawg_api_key`
   - `BASE_URL=https://api.rawg.io/api/` (or your RAWG base URL)
2. Sync the project and run the `app` configuration on a device or emulator.

---

## Running tests

```bash
./gradlew :feature:genres:testDebugUnitTest :feature:games:testDebugUnitTest :feature:game-details:testDebugUnitTest
```

