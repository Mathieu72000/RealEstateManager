package com.example.real_estate_manager.Room.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

    data class HouseInterestCrossRef(
        @Embedded val house: House,
        @Relation(
            parentColumn = "houseId",
            entityColumn = "interestId",
            associateBy = Junction(HouseInterestCrossRef::class)
        )
        val interestPoints: List<InterestPoints>
    )