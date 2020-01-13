package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class InterestPoints(
        @PrimaryKey(autoGenerate = true) val interestId: Long,
        @ColumnInfo val interestPoints: String?)
