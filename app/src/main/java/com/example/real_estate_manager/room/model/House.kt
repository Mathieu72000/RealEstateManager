package com.example.real_estate_manager.room.model

import androidx.room.*
import java.util.*

    @Entity(foreignKeys =
    [ForeignKey(entity = RealEstateAgent::class,
        parentColumns = arrayOf("agentId"),
        childColumns = arrayOf("agentId")),
        ForeignKey(entity = Type::class,
        parentColumns = arrayOf("typeId"),
        childColumns =  arrayOf("typeId"))])

    data class House (
        @PrimaryKey(autoGenerate = true)
        val houseId: Int,
        @ColumnInfo val price: Int,
        @ColumnInfo val surface: Int,
        @ColumnInfo val roomNumber: Int,
        @ColumnInfo val description: String,
        @ColumnInfo val location: String,
        @ColumnInfo val latitude: Double,
        @ColumnInfo val longitude: Double,
        @ColumnInfo val entryDate: String,
        @ColumnInfo val soldDate: String?,
        @ColumnInfo val agentId: Int,
        @ColumnInfo val typeId: Int)