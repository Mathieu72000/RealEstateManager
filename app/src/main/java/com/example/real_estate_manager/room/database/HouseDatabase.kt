package com.example.real_estate_manager.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.real_estate_manager.room.dao.*
import com.example.real_estate_manager.room.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Database(
    entities = [House::class, InterestPoints::class, Pictures::class, RealEstateAgent::class, Type::class],
    version = 1
)
abstract class HouseDatabase : RoomDatabase(), CoroutineScope {
    abstract fun houseDao(): HouseDao
    abstract fun interestPointsDao(): InterestPointsDao
    abstract fun picturesDao(): PicturesDao
    abstract fun realEstateAgentDao(): RealEstateAgentDao
    abstract fun typeDao(): TypeDao

    companion object {
        var INSTANCE: HouseDatabase? = null

        fun getInstance(context: Context): HouseDatabase? {
            if (INSTANCE == null) {
                synchronized(HouseDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HouseDatabase::class.java,
                        "houseDatabase"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    // ----------------------------------------------------------
    suspend fun getAllHouses(): List<House>? = this.houseDao().getAllHouses()

    fun insertHouse(house: House) {
        launch { insertNewHouse(house) }
    }

    private suspend fun insertNewHouse(house: House) =
        houseDao().insertHouse(house)

    // ----------------------------------------------------------
    suspend fun getAllInterestPoints(): List<InterestPoints> =
        this.interestPointsDao().getAllInterestPoints()

    fun insertInterestPoints(interestPoints: InterestPoints) {
        launch { newInterestPoints(interestPoints) }
    }

    private suspend fun newInterestPoints(interestPoints: InterestPoints) =
        interestPointsDao().insertInterestPoints(interestPoints)

    // ----------------------------------------------------------
    suspend fun getAllAgents(): List<RealEstateAgent> =
        this.realEstateAgentDao().getAllAgents()

    fun insertEstateAgents(realEstateAgent: RealEstateAgent) {
        launch { newEstateAgents(realEstateAgent) }
    }

    private suspend fun newEstateAgents(realEstateAgent: RealEstateAgent) =
        realEstateAgentDao().insertAgents(realEstateAgent)

    // ---------------------------------------------------------
    suspend fun getType(): Type = this.typeDao().getType()

    fun insertType(type: Type) {
        launch { newType(type) }
    }

    private suspend fun newType(type: Type) =
        typeDao().insertType(type)

    // ---------------------------------------------------------
    suspend fun getPictures(): List<Pictures> = this.picturesDao().getAllPictures()

    fun insertPictures(pictures: Pictures){
        launch { newPictures(pictures) }
    }

    suspend fun newPictures(pictures: Pictures) = picturesDao().insertPictures(pictures)
}