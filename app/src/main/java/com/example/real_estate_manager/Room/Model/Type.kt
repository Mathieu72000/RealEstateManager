package com.example.real_estate_manager.Room.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class Type(
        @PrimaryKey(autoGenerate = true) val typeId: Int,
        @ColumnInfo val type: String)
