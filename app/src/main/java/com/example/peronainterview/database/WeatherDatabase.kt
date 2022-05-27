package com.example.peronainterview.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [WeatherForecast::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase()  {
    abstract fun weatherDao(): WeatherDao

    private class WeatherDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val weatherDao = database.weatherDao()
                    // Delete all content here.
                    weatherDao.deleteAll()
                }
            }
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext,
                        WeatherDatabase::class.java,
                        "WeatherDatabase")
                    .addCallback(WeatherDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

class Converters{
    @TypeConverter
    fun listToJson(value: List<String>?)= Gson().toJson(value)!!
    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toMutableList()
}