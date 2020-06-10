package com.example.real_estate_manager.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.real_estate_manager.room.model.Pictures

@Dao
interface PicturesDao {

    @Insert
    suspend fun insertPictures(pictures: Pictures)

    @Update
    suspend fun updatePictures(pictures: Pictures)

    @Query("SELECT * FROM pictures WHERE housePictureId = :houseId")
    suspend fun getAllPictures(houseId: Long): List<Pictures>

    @Query("DELETE FROM pictures WHERE housePictureId=:houseId")
    suspend fun deletePictures(houseId: Long)

    @Transaction
    @Query("SELECT * FROM Pictures WHERE picturesId = :picturesId")
    fun getPicturesIdWithCursor(picturesId: Long): Cursor

    @Transaction
    @Query("SELECT * FROM Pictures")
    fun getAllPicturesWithCursor(): Cursor
}