package com.example.real_estate_manager.room.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HouseCrossRef(
    @Embedded
    val house: House,
    @Relation(
        entity = Type::class,
        parentColumn = "houseTypeId",
        entityColumn = "typeId"
    )
    val type: Type,
    @Relation(
        entity = RealEstateAgent::class,
        parentColumn = "houseAgentId",
        entityColumn = "agentId"
    )
    val realEstateAgent: RealEstateAgent,
    @Relation(
        parentColumn = "houseId",
        entityColumn = "interestId",
        associateBy = Junction(HouseAndInterestPoints::class)
    )
    val interestPoints: List<InterestPoints>,
    @Relation(
        entity = Pictures::class,
        parentColumn = "houseId",
        entityColumn = "housePictureId"
    )
    val pictures: List<Pictures>
)