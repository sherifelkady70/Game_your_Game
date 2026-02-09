plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

val rawgApiKey: String = run {
    val fromProject = project.findProperty("RAWG_API_KEY") as? String
    if (!fromProject.isNullOrBlank()) return@run fromProject.trim()
    val localFile = rootProject.file("local.properties")
    if (!localFile.exists()) return@run ""
    val line = localFile.readLines().firstOrNull { it.trimStart().startsWith("RAWG_API_KEY=") }
    val value = line?.substringAfter("=", "")?.trim()?.trim('"') ?: ""
    value.takeIf { it.isNotBlank() } ?: ""
}


android {

    namespace = "com.example.game_your_game"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.game_your_game"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Quoted so BuildConfig always has a valid string literal (never unquoted null)
        buildConfigField("String", "RAWG_API_KEY", "\"${rawgApiKey.replace("\\", "\\\\").replace("\"", "\\\"")}\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:genres"))
    implementation(project(":feature:games"))
    implementation(project(":feature:game-details"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}