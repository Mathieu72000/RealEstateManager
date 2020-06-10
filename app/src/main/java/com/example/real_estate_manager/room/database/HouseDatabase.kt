package com.example.real_estate_manager.room.database

import android.content.Context
import android.database.Cursor
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.real_estate_manager.room.dao.*
import com.example.real_estate_manager.room.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    abstract fun searchDao(): SearchDao

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
                            database.execSQL("INSERT into RealEstateAgent(realEstateAgent) VALUES('Patrick Moulin'), ('Ludovic Roland'), ('Mathieu Corroy'), ('Benoît Hayung')")
                            database.execSQL("INSERT into InterestPoints(interestPoints) VALUES('School'), ('Highschool'), ('Restaurant'), ('Hospital'), ('ATM'), ('Pharmacy'), ('Supermarket'), ('Monument'),('Church'), ('Mosque'), ('TownHall')")
                            database.execSQL("INSERT into Type(type) VALUES('House'), ('Flat'), ('Penthouse'), ('Duplex'), ('Villa')")
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

    suspend fun insertNewHouse(house: House): Long =
        houseDao().insertHouse(house)

    suspend fun updateHouse(house: House) =
        houseDao().updateHouse(house)

    // ----------------------------------------------------------

    suspend fun getHouseTypeAgent(houseId: Long): HouseCrossRef? =
        this.houseDao().getHouseAndTypeAndAgent(houseId)

    suspend fun getHouseTypeAgents(): List<HouseCrossRef> =
        this.houseDao().getAllHouseAndTypeAndAgent()

    // ----------------------------------------------------------
    suspend fun getAllInterestPoints(): List<InterestPoints> =
        this.interestPointsDao().getAllInterestPoints()

    suspend fun insertListInterestPoints(interestPoints: List<HouseAndInterestPoints>) =
        houseDao().insertListHouseInterestPoints(interestPoints)

    suspend fun deleteInterestPoints(houseId: Long) =
        interestPointsDao().deleteInterestPoints(houseId)

    // ----------------------------------------------------------
    suspend fun getAllAgents(): List<RealEstateAgent> =
        this.realEstateAgentDao().getAllAgents()

    // ---------------------------------------------------------
    suspend fun getType(): List<Type> = this.typeDao().getType()

    // ---------------------------------------------------------
    suspend fun insertPictures(pictures: List<Pictures>) = houseDao().insertPictures(pictures)

    suspend fun deletePictures(houseId: Long) = picturesDao().deletePictures(houseId)

    suspend fun getPictures(houseId: Long) = this.picturesDao().getAllPictures(houseId)

    // -------------------------------------------------------
    suspend fun getSearchHouses(resultId: List<Long>): List<HouseCrossRef> =
        this.houseDao().searchHouses(resultId)

    suspend fun searchHouses(query: String): List<Long> {
        val searchSQLiteQuery = SimpleSQLiteQuery(query)
        return searchDao().getHouses(searchSQLiteQuery)
    }

    // CONTENT PROVIDER

    fun getHouseIdWithCursor(houseId: Long): Cursor {
        return this.houseDao().getHouseIdWithCursor(houseId)
    }

    fun getAllHousesWithCursor(): Cursor {
        return this.houseDao().getAllHousesWithCursor()
    }

    fun contentProviderQuery(query: String): Cursor {
        val houseSQLiteQuery = SimpleSQLiteQuery(query)
        return this.houseDao().contentProviderQuery(houseSQLiteQuery)
    }

    fun getTypeIdWithCursor(typeId: Long): Cursor {
        return this.typeDao().getTypeIdWithCursor(typeId)
    }

    fun getAllTypeWithCursor(): Cursor {
        return this.typeDao().getAllTypeWithCursor()
    }

    fun getRealEstateAgentsIdWithCursor(realEstateAgentId: Long): Cursor {
        return this.realEstateAgentDao().getRealEstateAgentIdWithCursor(realEstateAgentId)
    }

    fun getAllRealEstateAgentWithCursor(): Cursor {
        return this.realEstateAgentDao().getAllRealEstateAgentWithCursor()
    }

    fun getPicturesIdWithCursor(picturesId: Long): Cursor {
        return this.picturesDao().getPicturesIdWithCursor(picturesId)
    }

    fun getAllPicturesWithCursor(): Cursor {
        return this.picturesDao().getAllPicturesWithCursor()
    }

    fun getInterestPointsIdWithCursor(interestPointsId: Long): Cursor {
        return this.interestPointsDao().getInterestPointsIdWithCursor(interestPointsId)
    }

    fun getAllInterestPointsWithCursor(): Cursor {
        return this.interestPointsDao().getAllInterestPointsWithCursor()
    }
}