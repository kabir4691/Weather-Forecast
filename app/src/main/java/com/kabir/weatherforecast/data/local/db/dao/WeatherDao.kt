package com.kabir.weatherforecast.data.local.db.dao

import androidx.room.*
import com.kabir.weatherforecast.data.local.db.entity.WeatherEntity
import io.reactivex.Single


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: WeatherEntity): Single<Long>

    @Update
    fun update(user: WeatherEntity): Single<Int>

    @Delete
    fun delete(user: WeatherEntity): Single<Int>

    @Insert
    fun insertMany(vararg users: WeatherEntity): Single<List<Long>>

    @Query("SELECT * FROM weather_entity")
    fun getAll(): Single<List<WeatherEntity>>

    @Query("SELECT * FROM weather_entity WHERE input = :input LIMIT 1")
    fun getByInput(input: String): Single<WeatherEntity>

    @Query("SELECT count(*) FROM weather_entity")
    fun count(): Single<Int>
}