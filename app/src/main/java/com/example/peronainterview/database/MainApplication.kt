package com.example.peronainterview.database

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.peronainterview.viewmodel.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainApplication: Application(), ViewModelStoreOwner {

    private val appViewModelStore: ViewModelStore by lazy { ViewModelStore() }
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val database by lazy { WeatherDatabase.getInstance(this, applicationScope) }
    private val sp by lazy { SharedPreferencesManager.getInstance(this) }
    val repository: MainRepository by lazy { MainRepository(ioDispatcher, database.weatherDao(), sp) }
    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

}