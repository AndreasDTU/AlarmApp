package com.example.alarmapp

import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("endpoint")
    suspend fun getData(@Query("param") param: String): DataModel
}