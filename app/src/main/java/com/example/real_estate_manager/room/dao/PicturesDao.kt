package com.example.real_estate_manager.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}