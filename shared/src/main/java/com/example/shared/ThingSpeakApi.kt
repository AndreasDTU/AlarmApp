package com.example.shared


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ThingSpeakApi {
    @GET("channels/{id}/feeds.json")
    suspend fun getFeeds(
        @Path("id") channelId: Int,
        @Query("api_key") apiKey: String
    ): ThingSpeakResponse
}