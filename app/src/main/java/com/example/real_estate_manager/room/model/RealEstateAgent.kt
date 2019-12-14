package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class RealEstateAgent(
        @PrimaryKey(autoGenerate = true) val agentId: Int,
        @ColumnInfo val realEstateAgent: String)