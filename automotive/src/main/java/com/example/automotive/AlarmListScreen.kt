package com.example.automotive

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template

class AlarmListScreen(carContext: CarContext) : Screen(carContext) {

    override fun onGetTemplate(): Template {
        val alarms = listOf("Test Alarm 1", "Test Alarm 2")

        val list = ItemList.Builder()
        alarms.forEach { alarm ->
            list.addItem(
                Row.Builder()
                    .setTitle(alarm)
                    .build()
            )
        }

        return ListTemplate.Builder()
            .setSingleList(list.build())
            .build()
    }
}