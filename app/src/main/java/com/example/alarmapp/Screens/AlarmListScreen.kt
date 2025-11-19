package com.example.alarmapp.Screens;

import android.inputmethodservice.Keyboard
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Template

public class AlarmListScreen(carContext: Any?) {
    fun onGetTemplate(): Template {
        val itemList = ItemList.Builder();

        // Example alarm items — replace with your ViewModel data
        val alarms = listOf("Compressor broke", "Idk", "073");

        //These alarms should be shown somehow idk


        return ListTemplate.Builder()
                .setSingleList(itemList.build())
                .build()
    }
}

