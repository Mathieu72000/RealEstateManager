package com.example.real_estate_manager.Room.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class InterestPoints(
        @PrimaryKey(autoGenerate = true) val interestId: Int,
        @ColumnInfo val interestPoints: String)
