package com.example.peronainterview.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather")
data class WeatherForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTime: String,
    val endTime: String,
    val temperature: String
): Serializable
