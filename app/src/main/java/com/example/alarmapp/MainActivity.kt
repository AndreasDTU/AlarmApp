package com.example.alarmapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapp.Screens.AlarmListScreen
import com.example.alarmapp.ui.theme.AlarmAppTheme
import com.example.shared.AlarmRepository
import com.example.shared.AlarmViewModel
import com.example.shared.AlarmViewModelFactory
import com.example.shared.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, can send notifications
        } else {
            // Permission denied
        }
    }
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                }
                else -> {
                    // Request permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
        debugPrintAllFeeds()
        // Step 1: Create the repository
        val repository = AlarmRepository(RetrofitClient.api)

        // Step 2: Create the ViewModel Factory
        val factory = AlarmViewModelFactory(repository)

        setContent {
            AlarmAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var selectedTrailerId by remember { mutableStateOf<Int?>(null) }

                    Box(modifier = Modifier.padding(innerPadding)) {

                        if (selectedTrailerId == null) {
                            // Show trailer selection dropdown
                            SelectTrailerScreen { id ->
                                selectedTrailerId = id
                            }
                        } else {
                            // Show the Alarm List Screen
                            val viewModel: AlarmViewModel = viewModel(factory = factory)
                            AlarmListScreen(
                                trailerId = selectedTrailerId!!,
                                viewModel = viewModel,
                                onBackClick = { selectedTrailerId = null }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SelectTrailerScreen(onTrailerSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Please select your current trailer:",
            style = MaterialTheme.typography.titleMedium,
        )

        TrailerDropdownMenu(onTrailerSelected)
    }
}

@Composable
fun TrailerDropdownMenu(onTrailerSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    // Example trailer IDs
    val trailerIds = List(10) { it + 1 }

    Box() {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.Black,
                shape = RectangleShape
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Trailer"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            trailerIds.forEach { id ->
                DropdownMenuItem(
                    text = { Text("Trailer $id") },
                    onClick = {
                        expanded = false
                        onTrailerSelected(id)
                    }
                )
            }
        }
    }
}
fun debugPrintAllFeeds() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Replace with your actual channel ID and API key
            val channelId = 3160644
            val apiKey = "27IMKHF43VBWOLTZ"

            val response = RetrofitClient.api.getFeeds(channelId, apiKey)

            Log.d("DEBUG", "Total feeds: ${response.feeds.size}")

            response.feeds.forEach { feed ->
                Log.d("DEBUG", """
                    Entry ID: ${feed.entry_id}
                    Created At: ${feed.created_at}
                    Truck ID: ${feed.field1}
                    Alarm Code: ${feed.field2}
                    Alarm Desc: ${feed.field3}
                    Alarm Type: ${feed.field4}
                    Alarm State: ${feed.field5}
                    Temperature: ${feed.field6}
                """.trimIndent())
            }
        } catch (e: Exception) {
            Log.e("DEBUG", "Error fetching ThingSpeak feeds", e)
        }
    }
}