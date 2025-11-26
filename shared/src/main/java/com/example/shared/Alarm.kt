package com.example.shared

data class Alarm(
    val truckId: Int,
    val alarmCode: Int,
    val alarmDescription: String,
    val alarmType: String,
    val alarmState: String,
    val temperature: Float,
    val createdAt: String
)