package com.example.real_estate_manager.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.real_estate_manager.room.model.InterestPoints

@Dao
interface InterestPointsDao {

    @Insert
    fun insertInterestPoints(interestPoints: InterestPoints)

    @Update
    fun updateInterestPoints(interestPoints: InterestPoints)

    @Query("SELECT * FROM interestPoints")
    suspend fun getAllInterestPoints(): List<InterestPoints>

    @Transaction
    @Query("DELETE FROM houseandinterestpoints WHERE houseId=:houseId")
    suspend fun deleteInterestPoints(houseId: Long)
}