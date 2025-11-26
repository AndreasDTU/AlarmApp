package com.example.alarmapp.Screens;

import android.app.Activity
import android.inputmethodservice.Keyboard
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Template
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shared.Alarm
import com.example.shared.AlarmViewModel
import com.example.shared.AlarmRepository

@Composable
fun AlarmListScreen(
    trailerId: Int,
    viewModel: AlarmViewModel
) {
    val context = LocalContext.current
    val activity = context as? Activity
    // Load alarms when the screen is first composed
    LaunchedEffect(trailerId) {
        viewModel.loadAlarms(trailerId)
    }

    val alarms = viewModel.alarms

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
    Button(
        onClick = { activity?.finish() }, // Ends this activity and goes back
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Back")
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
