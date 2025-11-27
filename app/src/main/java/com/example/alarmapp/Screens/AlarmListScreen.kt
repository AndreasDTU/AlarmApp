package com.example.alarmapp.Screens;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shared.Alarm
import com.example.shared.AlarmViewModel

@Composable
fun AlarmListScreen(
    trailerId: Int,
    viewModel: AlarmViewModel,
    onBackClick: () -> Unit
) {
    // Load alarms when the screen is first composed
    LaunchedEffect(trailerId) {
        viewModel.loadAlarms(trailerId)
    }

    val alarms = viewModel.alarms
    Column(Modifier.padding(16.dp)) {
        Button(
            onClick = onBackClick , // Ends this activity and goes back
        ) {
            Text("Back")
        }

        if (alarms.isEmpty()) {
            // Show "No alarms" when the list is empty
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No alarms")
            }
        } else {
            // Show the list of alarms
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(alarms) { alarm ->
                    AlarmItem(alarm)
                }
            }
        }
    }
}

@Composable
fun AlarmItem(alarm: Alarm) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Truck ID: ${alarm.truckId}")
        Text(text = "Alarm Code: ${alarm.alarmCode}")
        Text(text = "Description: ${alarm.alarmDescription}")
        Text(text = "Type: ${alarm.alarmType}")
        Text(text = "State: ${alarm.alarmState}")
        Text(text = "Temperature: ${alarm.temperature}")
        val time = alarm.createdAt
        val split = time.split("T")
        val split1 = split[1].split("Z")
        Text(text = "Created At: ${split[0]}")
        Text(text = "Time: ${split1[0]}")
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
