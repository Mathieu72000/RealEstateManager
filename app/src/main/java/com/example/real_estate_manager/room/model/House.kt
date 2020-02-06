package com.example.real_estate_manager.room.model

import androidx.room.*

@Entity(
    foreignKeys =
    [ForeignKey(
        entity = RealEstateAgent::class,
        parentColumns = arrayOf("agentId"),
        childColumns = arrayOf("houseAgentId")
    ),
        ForeignKey(
            entity = Type::class,
            parentColumns = arrayOf("typeId"),
            childColumns = arrayOf("houseTypeId")
        )],
    indices = [Index("houseAgentId"), Index("houseTypeId")]
)

data class House(
    @PrimaryKey(autoGenerate = true) val houseId: Long,
    @ColumnInfo val houseAgentId: Long,
    @ColumnInfo val houseTypeId: Long,
    @ColumnInfo val price: Int?,
    @ColumnInfo val surface: Int?,
    @ColumnInfo val roomNumber: Int?,
    @ColumnInfo val description: String?,
    @ColumnInfo val location: String?,
    @ColumnInfo val latitude: Double?,
    @ColumnInfo val longitude: Double?,
    @ColumnInfo val entryDate: String?,
    @ColumnInfo val soldDate: String?
)
