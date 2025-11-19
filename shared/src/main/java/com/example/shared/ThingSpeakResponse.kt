package com.example.shared

import com.google.gson.annotations.SerializedName

data class ThingSpeakResponse(
    val feeds: List<ThingSpeakFeed>
)

data class ThingSpeakFeed(
    @SerializedName("field1") val alarmText: String?
)