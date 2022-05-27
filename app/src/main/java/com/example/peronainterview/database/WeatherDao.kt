package com.example.peronainterview.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("DELETE FROM weather")
    suspend fun deleteAll()

    @Query("SELECT * FROM weather ORDER BY id" )
    fun getAll(): List<WeatherForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(pokemonList: List<WeatherForecast>)
}