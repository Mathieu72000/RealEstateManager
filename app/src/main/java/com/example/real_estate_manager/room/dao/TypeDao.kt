package com.example.real_estate_manager.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.room.model.Type

@Dao
interface TypeDao {

    @Insert
    fun insertType(type: Type)

    @Update
    fun updateType(type: Type)

    @Query("SELECT * FROM type")
    suspend fun getType(): List<Type>
}