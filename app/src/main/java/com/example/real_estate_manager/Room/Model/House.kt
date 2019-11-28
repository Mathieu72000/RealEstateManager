package com.example.real_estate_manager.Room.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

    @Entity(foreignKeys = arrayOf(
        ForeignKey(entity = RealEstateAgent::class,
        parentColumns = arrayOf("agentId"),
        childColumns = arrayOf("agentId")),

        ForeignKey(entity = Type::class,
            parentColumns = arrayOf("typeId"),
            childColumns =  arrayOf("typeId"))))

    data class House (
        @PrimaryKey(autoGenerate = true) val houseId: Int,
        @ColumnInfo val price: Int,
        @ColumnInfo val surface: Int,
        @ColumnInfo val roomNumber: Int,
        @ColumnInfo val description: String,
        @ColumnInfo val address: String,
        @ColumnInfo val latitude: Double,
        @ColumnInfo val longitude: Double,
        @ColumnInfo val entryDate: Date,
        @ColumnInfo val soldDate: Date?,
        @ColumnInfo val agentId: Int,
        @ColumnInfo val typeId: Int)