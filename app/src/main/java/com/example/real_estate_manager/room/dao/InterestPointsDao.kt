package com.example.real_estate_manager.room.dao

import android.database.Cursor
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

    @Transaction
    @Query("SELECT * FROM InterestPoints WHERE interestId = :interestId")
    fun getInterestPointsIdWithCursor(interestId: Long): Cursor

    @Transaction
    @Query("SELECT * FROM InterestPoints")
    fun getAllInterestPointsWithCursor(): Cursor
}