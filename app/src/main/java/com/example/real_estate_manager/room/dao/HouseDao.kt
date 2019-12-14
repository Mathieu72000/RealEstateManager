package com.example.real_estate_manager.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.room.model.House

@Dao
interface HouseDao {

    @Insert
    suspend fun insertHouse(house: House)

    @Update
    suspend fun updateHouse(house: House)

    @Query("SELECT * FROM house")
    suspend fun getAllHouses(): List<House>
}