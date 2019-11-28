package com.example.real_estate_manager.Room.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

    @Entity(foreignKeys = arrayOf(ForeignKey(entity = House::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("picturesId"))))
    data class Pictures(
        @PrimaryKey(autoGenerate = true) val picturesId: Int,
        @ColumnInfo val pictures: String,
        @ColumnInfo val houseId: Int)