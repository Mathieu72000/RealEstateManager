package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class Type(
        @PrimaryKey(autoGenerate = true) val typeId: Long,
        @ColumnInfo val type: String?)
