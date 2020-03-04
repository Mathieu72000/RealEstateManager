package com.example.real_estate_manager.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.real_estate_manager.room.model.RealEstateAgent

@Dao
interface RealEstateAgentDao {

    @Insert
    fun insertAgents(realEstateAgent: RealEstateAgent)

    @Update
    fun updateAgent(realEstateAgent: RealEstateAgent)

    @Query("SELECT * FROM realestateagent")
    suspend fun getAllAgents(): List<RealEstateAgent>
}