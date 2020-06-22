package com.example.real_estate_manager.room.database

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.dao.*
import com.example.real_estate_manager.room.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.ByteArrayOutputStream
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
                            super.onCreate(database)                                                 //Index 1 ↓            Index 2 ↓        Index 3 ↓   etc...
                            val bitmap = BitmapFactory.decodeResource(
                                context.resources,
                                R.drawable.house_interior
                            )
                            val byteArray = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                            val toByteArray = byteArray.toByteArray()
                            val base64 = Base64.encodeToString(toByteArray, Base64.DEFAULT)
                            database.execSQL("INSERT into RealEstateAgent(realEstateAgent) VALUES('Patrick Moulin'), ('Ludovic Roland'), ('Mathieu Corroy'), ('Benoît Hayung')")
                            database.execSQL("INSERT into InterestPoints(interestPoints) VALUES('School'), ('Highschool'), ('Restaurant'), ('Hospital'), ('ATM'), ('Pharmacy'), ('Supermarket'), ('Monument'),('Church'), ('Mosque'), ('TownHall')")
                            database.execSQL("INSERT into Type(type) VALUES('House'), ('Flat'), ('Duplex'), ('Villa')")
                            database.execSQL("INSERT into House(price, roomNumber, surface, description, location, latitude, longitude, entryDate, houseAgentId, houseTypeId) VALUES(100000, 3, 80, 'Beautiful flat, close to the university of Le Mans', '6 Rue du manoir, 72000 Le Mans, France', 48.011084, 0.162892, '24/08/2019' , 2, 2) ")
                            database.execSQL("INSERT into Pictures(pictures, pictureText, housePictureId)VALUES('${base64}', 'Living room', 1)")
                            database.execSQL("INSERT into HouseAndInterestPoints(houseId, interestId) VALUES(1, 1), (1, 2), (1, 3)")
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

    fun contentProviderQuery(query: String): Cursor {
        val houseSQLiteQuery = SimpleSQLiteQuery(query)
        return this.houseDao().contentProviderQuery(houseSQLiteQuery)
    }
}