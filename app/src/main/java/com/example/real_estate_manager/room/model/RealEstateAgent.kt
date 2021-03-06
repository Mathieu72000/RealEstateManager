package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RealEstateAgent(
    @PrimaryKey(autoGenerate = true) val agentId: Long,
    @ColumnInfo val realEstateAgent: String?
) {
    override fun toString(): String {
        return realEstateAgent.toString()
    }
}