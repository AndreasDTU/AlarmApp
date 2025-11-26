package com.example.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlarmRepository(private val api: ThingSpeakApi) {
    
    suspend fun getAlarms(trailerId: Int): List<Alarm> {
        val response = api.getFeeds(
            channelId = 3160644,      // your channel ID
            apiKey = "27IMKHF43VBWOLTZ"
        )

        return response.feeds
            .mapNotNull { feed ->
                val id = feed.field1?.toIntOrNull()
                if (id == trailerId) {
                    Alarm(
                        truckId = id,
                        alarmCode = feed.field2?.toIntOrNull() ?: 0,
                        alarmDescription = feed.field3 ?: "",
                        alarmType = feed.field4 ?: "",
                        alarmState = feed.field5 ?: "",
                        temperature = feed.field6?.toFloatOrNull() ?: 0f,
                        createdAt = feed.created_at ?: ""
                    )
                } else null
            }
    }
}