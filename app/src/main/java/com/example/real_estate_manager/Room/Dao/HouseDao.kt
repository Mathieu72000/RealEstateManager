package com.example.real_estate_manager.Room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.Room.Model.House

@Dao
interface HouseDao {

    @Insert
    fun insertHouse(house: House)

    @Update
    fun updateHouse(house: House)

    @Query("SELECT * FROM house")
    fun getAllHouses(): List<House>
}