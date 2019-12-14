package com.example.real_estate_manager.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.room.model.InterestPoints

@Dao
interface InterestPointsDao {

    @Insert
    fun insertInterestPoints(interestPoints: InterestPoints)

    @Update
    fun updateInterestPoints(interestPoints: InterestPoints)

    @Query("SELECT * FROM interestpoints")
    fun getAllInterestPoints(): List<InterestPoints>
}