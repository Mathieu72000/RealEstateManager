package com.example.real_estate_manager.room.model

import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = House::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("houseId"))], indices = [Index("picturesId"), Index("houseId")]
    )
    data class Pictures(
        @PrimaryKey(autoGenerate = true) val picturesId: Long = 0,
        @ColumnInfo val pictures: String?,
        @ColumnInfo val pictureText: String?,
        @ColumnInfo val houseId: Long?)