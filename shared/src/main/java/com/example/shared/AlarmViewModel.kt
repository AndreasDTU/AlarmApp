package com.example.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val channelId: Int,
    private val apiKey: String
) : ViewModel() {

    val alarms = AlarmRepository.alarms

    init {
        viewModelScope.launch {
            while (true) {
                AlarmRepository.updateAlarms(channelId, apiKey)
                delay(10_000)  // every 10 seconds
            }
        }
    }
}