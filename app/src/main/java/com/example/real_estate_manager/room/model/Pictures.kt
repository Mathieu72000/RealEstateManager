package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

    @Entity(foreignKeys = [ForeignKey(entity = House::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("picturesId"))]
    )
    data class Pictures(
        @PrimaryKey(autoGenerate = true) val picturesId: Long,
        @ColumnInfo val pictures: String?,
        @ColumnInfo val houseId: Int?)