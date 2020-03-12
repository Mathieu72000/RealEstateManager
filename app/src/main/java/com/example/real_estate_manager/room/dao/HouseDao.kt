package com.example.real_estate_manager.room.dao

import androidx.room.*
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.HouseAndInterestPoints
import com.example.real_estate_manager.room.model.HouseCrossRef
import com.example.real_estate_manager.room.model.Pictures

@Dao
interface HouseDao {

    // -------- INSERT ---------

    @Insert
    suspend fun insertHouse(house: House): Long

    @Insert
    suspend fun insertListHouseInterestPoints(interest: List<HouseAndInterestPoints>)

    @Insert
    suspend fun insertPictures(pictures: List<Pictures>)

    // --------- UPDATE ---------

    @Update
    suspend fun updateHouse(house: House)

    // ---------- QUERY ---------

    @Transaction
    @Query("SELECT * FROM House")
    suspend fun getAllHouseAndTypeAndAgent(): List<HouseCrossRef>

    @Transaction
    @Query("SELECT * FROM House WHERE houseId = :houseId")
    suspend fun getHouseAndTypeAndAgent(houseId: Long): HouseCrossRef
}