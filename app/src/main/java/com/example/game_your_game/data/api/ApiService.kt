package com.example.game_your_game.data.api

import retrofit2.http.GET

/**
 * Sample API interface. Replace with your real endpoints and models.
 * Use suspend functions for Coroutines or return Flow/Response types as needed.
 */
interface ApiService {

    @GET("example")
    suspend fun getExample(): ExampleResponse
}

data class ExampleResponse(
    val id: String? = null,
    val name: String? = null
)
