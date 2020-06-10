package com.example.real_estate_manager.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.real_estate_manager.room.model.RealEstateAgent

@Dao
interface RealEstateAgentDao {

    @Insert
    fun insertAgents(realEstateAgent: RealEstateAgent)

    @Update
    fun updateAgent(realEstateAgent: RealEstateAgent)

    @Query("SELECT * FROM realestateagent")
    suspend fun getAllAgents(): List<RealEstateAgent>

    @Transaction
    @Query("SELECT * FROM RealEstateAgent WHERE agentId = :agentId")
    fun getRealEstateAgentIdWithCursor(agentId: Long): Cursor

    @Transaction
    @Query("SELECT * FROM RealEstateAgent")
    fun getAllRealEstateAgentWithCursor(): Cursor
}