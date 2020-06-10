package com.example.real_estate_manager.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.real_estate_manager.room.model.Type

@Dao
interface TypeDao {

    @Insert
    fun insertType(type: Type)

    @Update
    fun updateType(type: Type)

    @Query("SELECT * FROM type")
    suspend fun getType(): List<Type>

    @Transaction
    @Query("SELECT * FROM Type WHERE typeId = :typeId")
    fun getTypeIdWithCursor(typeId: Long): Cursor

    @Transaction
    @Query("SELECT * FROM Type")
    fun getAllTypeWithCursor(): Cursor
}