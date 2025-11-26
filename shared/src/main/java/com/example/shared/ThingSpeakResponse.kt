package com.example.shared

import com.google.gson.annotations.SerializedName

data class ThingSpeakResponse(
    val channel: Channel,
    val feeds: List<ThingSpeakFeed>
)
data class Channel(
    val id: Int,
    val name: String
)
data class ThingSpeakFeed(
    val entry_id: Int,
    val created_at: String,
    val field1: String?,
    val field2: String?,
    val field3: String?,
    val field4: String?,
    val field5: String?,
    val field6: String?
)