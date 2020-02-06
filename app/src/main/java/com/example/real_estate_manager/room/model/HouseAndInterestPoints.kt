package com.example.real_estate_manager.room.model

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["houseId", "interestId"],indices = [Index("houseId"), Index("interestId")])
data class HouseAndInterestPoints(
    val houseId: Long,
    val interestId: Long
)