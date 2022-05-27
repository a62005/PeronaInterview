package com.example.peronainterview.viewmodel

import com.example.peronainterview.database.SharedPreferencesManager
import com.example.peronainterview.database.WeatherDao
import com.example.peronainterview.network.OkHttp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MainRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: WeatherDao,
    private val sp: SharedPreferencesManager
) {

    private val okHttp: OkHttp by lazy { OkHttp() }

    fun getLaunchTimes(): Int {
        var times = sp.getLaunchTimes()
        return if (times == null) {
            times = 1
            sp.setLaunchTimes(times)
            times
        } else {
            sp.setLaunchTimes(times++!!)
            times
        }
    }

    suspend fun getWeatherList() = withContext(ioDispatcher) {
        val localList = dao.getAll()
        if (localList.isNotEmpty()) {
            localList
        } else {
            val remoteList = okHttp.getWeatherData()
            dao.saveAll(remoteList)
            remoteList
        }
    }
}