package com.example.real_estate_manager.room.dao

import androidx.room.*
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.HouseTypeAgent

@Dao
interface HouseDao {

    @Insert
    suspend fun insertHouse(house: House)

    @Update
    suspend fun updateHouse(house: House)

    @Transaction
    @Query("SELECT * FROM House")
    suspend fun getAllHouseAndTypeAndAgent(): List<HouseTypeAgent>
}