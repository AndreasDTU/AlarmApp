package com.example.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AlarmRepository {

    private val _alarms = MutableStateFlow<List<String>>(emptyList())
    val alarms: StateFlow<List<String>> = _alarms

    suspend fun updateAlarms(channelId: Int, apiKey: String) {
        try {
            val response = RetrofitClient.api.getFeeds(channelId, apiKey)

            val parsed = response.feeds
                .mapNotNull { it.alarmText }
                .filter { it.isNotBlank() }

            _alarms.value = parsed

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}