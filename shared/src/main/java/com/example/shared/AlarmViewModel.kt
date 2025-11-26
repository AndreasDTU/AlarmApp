package com.example.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    var alarms by mutableStateOf<List<Alarm>>(emptyList())
        private set

    fun loadAlarms(trailerId: Int) {
        viewModelScope.launch {
            alarms = repository.getAlarms(trailerId)
        }
    }
}
