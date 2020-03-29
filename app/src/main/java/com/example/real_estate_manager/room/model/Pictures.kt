package com.example.real_estate_manager.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = House::class,
        parentColumns = arrayOf("houseId"),
        childColumns = arrayOf("housePictureId")
    )]
)

data class Pictures(
    @PrimaryKey(autoGenerate = true) val picturesId: Long = 0,
    @ColumnInfo val pictures: String?,
    @ColumnInfo val pictureText: String?,
    @ColumnInfo val housePictureId: Long?
)