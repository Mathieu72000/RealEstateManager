package com.example.real_estate_manager.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.room.model.Pictures

@Dao
interface PicturesDao {

    @Insert
    fun insertPictures(pictures: Pictures)

    @Update
    fun updatePictures(pictures: Pictures)

    @Query("SELECT * FROM pictures")
    fun getAllPictures(): List<Pictures>
}