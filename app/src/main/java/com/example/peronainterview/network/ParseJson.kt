package com.example.peronainterview.network

import com.example.peronainterview.database.WeatherForecast
import org.json.JSONObject

class ParseJson {


    fun parseWeather(string: String): List<WeatherForecast> {
        val list = mutableListOf<WeatherForecast>()
        val json = JSONObject(string)
        if (json.has("records")) {
            val records = json.getJSONObject("records")
            if (records.has("location")) {
                val locations = records.getJSONArray("location")
                List(locations.length()) { locationIndex ->
                    val location = locations.getJSONObject(locationIndex)
                    if (location.has("weatherElement")) {
                        val weathers = location.getJSONArray("weatherElement")
                        List(weathers.length()) { weatherIndex ->
                            val weather = weathers.getJSONObject(weatherIndex)
                            if (weather.has("time")) {
                                val times = weather.getJSONArray("time")
                                List(times.length()) { timeIndex ->
                                    val time = times.getJSONObject(timeIndex)
                                    val startTime = time.getString("startTime").replace('-', '/')
                                    val endTime = time.getString("endTime").replace('-', '/')
                                    time.getJSONObject("parameter").let {
                                        val num = it.getString("parameterName")
                                        val unit = it.getString("parameterUnit")
                                        val weatherForecast = WeatherForecast(
                                            startTime = startTime,
                                            endTime = endTime,
                                            temperature = "$num$unit"
                                        )
                                        list.add(weatherForecast)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list
    }
}