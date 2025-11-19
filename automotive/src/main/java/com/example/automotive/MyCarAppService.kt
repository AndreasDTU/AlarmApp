package com.example.automotive
import android.content.Intent
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator
import  com.example.automotive.MainCarSession


abstract class MyCarAppService : CarAppService() {

    override fun onCreateSession(): Session {
        return object : Session() {
            override fun onCreateScreen(intent: Intent): Screen {
                return AlarmListScreen(carContext)
            }
        }
    }
}