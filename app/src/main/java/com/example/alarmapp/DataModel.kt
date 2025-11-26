package com.example.alarmapp

data class DataModel(
    val freezer_number: Int,
    val alarm_code: String,
    val alarm_desc: String,
    val alarm_type: Int,
    val temperature: Int
)