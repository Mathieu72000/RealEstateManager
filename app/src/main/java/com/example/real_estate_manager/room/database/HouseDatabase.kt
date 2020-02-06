package com.example.real_estate_manager.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.real_estate_manager.room.dao.*
import com.example.real_estate_manager.room.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Database(
    entities = [House::class, InterestPoints::class, Pictures::class, RealEstateAgent::class, Type::class, HouseAndInterestPoints::class],
    version = 1, exportSchema = false
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
                    ).addCallback(object : Callback() {
                        override fun onCreate(database: SupportSQLiteDatabase) {
                            super.onCreate(database)                                            //Index 1 ↓    Index 2 ↓    Index 3 ↓   etc...
                            database.execSQL("INSERT into RealEstateAgent(realEstateAgent) VALUES('Patrick'), ('Ludovic'), ('Mathieu'), ('Benoît')")
                            database.execSQL("INSERT into InterestPoints(interestPoints) VALUES('School'), ('Highschool'), ('Restaurant'), ('Hospital'), ('ATM'), ('Pharmacy'), ('Supermarket'), ('Monument')")
                            database.execSQL("INSERT into Type(type) VALUES('House'), ('Flat'), ('Penthouse'), ('Duplex'), ('Villa')")
                            database.execSQL("INSERT into House (price, roomNumber, surface, description, location, houseAgentId, houseTypeId) VALUES (100000, 3, '80', 'Petite maison fonctionnelle', '12 allée du manoir', 2, 1) ")
                            database.execSQL("INSERT into House (price, roomNumber, surface, description, location, houseAgentId, houseTypeId) VALUES (250000, 8, '150', 'Superbe villa, très jolie', '8, rue des lilas', 1, 5) ")
                            database.execSQL("INSERT into House (price, roomNumber, surface, description, location, houseAgentId, houseTypeId, soldDate) VALUES (800000, 10, '300', 'Énorme appartement comprenant une terrasse', '30, rue des bourges', 3, 3, '21/08/2019') ")
                            database.execSQL("INSERT into HouseAndInterestPoints(houseId, interestId) VALUES(1, 2), (1, 3)")
                        }
                    }).build()
                }
            }
            return INSTANCE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    // ----------------------------------------------------------
     suspend fun getHouseTypeAgent(houseId: Long): HouseTypeAgent? =
        this.houseDao().getHouseAndTypeAndAgent(houseId)

     suspend fun insertNewHouse(house: House) =
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
    suspend fun getType(): List<Type> = this.typeDao().getType()

    fun insertType(type: Type) {
        launch { newType(type) }
    }

    private suspend fun newType(type: Type) =
        typeDao().insertType(type)

    // ---------------------------------------------------------
    suspend fun getPictures(): List<Pictures> = this.picturesDao().getAllPictures()

    fun insertPictures(pictures: Pictures) {
        launch { newPictures(pictures) }
    }

    private suspend fun newPictures(pictures: Pictures) = picturesDao().insertPictures(pictures)
    // ---------------------------------------------------------

    suspend fun getHouseTypeAgents(): List<HouseTypeAgent> =
        this.houseDao().getAllHouseAndTypeAndAgent()
}