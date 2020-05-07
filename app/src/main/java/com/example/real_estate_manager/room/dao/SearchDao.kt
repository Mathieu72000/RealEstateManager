package com.example.real_estate_manager.room.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface SearchDao {

    @RawQuery
    suspend fun getHouses(query: SupportSQLiteQuery): List<Long>
}