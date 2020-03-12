package com.example.real_estate_manager.room.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["houseId", "interestId"],
    foreignKeys = [ForeignKey(
        entity = House::class,
        parentColumns = ["houseId"],
        childColumns = ["houseId"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = InterestPoints::class,
            parentColumns = ["interestId"],
            childColumns = ["interestId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("houseId"), Index("interestId")]
)
data class HouseAndInterestPoints(
    val houseId: Long = 0,
    val interestId: Long = 0
)