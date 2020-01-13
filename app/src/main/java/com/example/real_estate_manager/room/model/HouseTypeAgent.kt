package com.example.real_estate_manager.room.model

import androidx.room.Embedded
import androidx.room.Relation

data class HouseTypeAgent(
    @Embedded
    val house: House,
    @Relation(entity = Type::class, parentColumn = "houseTypeId", entityColumn = "typeId")
    val type: Type,
    @Relation(entity = RealEstateAgent::class, parentColumn = "houseAgentId", entityColumn = "agentId")
    val realEstateAgent: RealEstateAgent
)