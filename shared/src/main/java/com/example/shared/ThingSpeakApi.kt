package com.example.shared


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ThingSpeakApi {

    @GET("channels/{channelId}/feeds.json")
    suspend fun getFeeds(
        @Path("channelId") channelId: Int,
        @Query("api_key") apiKey: String,
        @Query("results") results: Int = 50
    ): ThingSpeakResponse
}
