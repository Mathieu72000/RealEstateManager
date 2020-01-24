package com.example.real_estate_manager.room.model

import androidx.room.Entity

@Entity(primaryKeys = ["interestId", "houseId"])
data class HouseAndInterestPoints(
    val interestId: Long,
    val houseId: Long
)