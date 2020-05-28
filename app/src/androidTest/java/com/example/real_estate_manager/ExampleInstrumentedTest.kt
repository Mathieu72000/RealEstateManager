package com.example.real_estate_manager

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.HouseAndInterestPoints
import com.example.real_estate_manager.room.model.Pictures
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val application by lazy { ApplicationProvider.getApplicationContext<Application>() }

    private lateinit var houseDatabase: HouseDatabase

    @Before
    fun initDatabase() {
        houseDatabase = Room.inMemoryDatabaseBuilder(application, HouseDatabase::class.java)
            .allowMainThreadQueries().addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(database: SupportSQLiteDatabase) {
                    super.onCreate(database)
                    database.execSQL("INSERT into RealEstateAgent(realEstateAgent) VALUES('Patrick Moulin'), ('Ludovic Roland'), ('Mathieu Corroy'), ('Benoît Hayung');")
                    database.execSQL("INSERT into InterestPoints(interestPoints) VALUES('School'), ('Highschool'), ('Restaurant'), ('Hospital'), ('ATM'), ('Pharmacy'), ('Supermarket'), ('Monument'), ('Church'), ('Mosque'), ('TownHall');")
                    database.execSQL("INSERT into Type(type) VALUES('House'), ('Flat'), ('Penthouse'), ('Duplex'), ('Villa');")
                }
            }).build()
    }

    @After
    fun closeDatabase() {
        houseDatabase.close()
    }

    @Test
    fun testHouse() = runBlocking {

        // INSERT

        val house1 = House(
            0,
            1,
            1,
            100000,
            60,
            3,
            "Petite maison de bourg",
            "sainte-jamme",
            0.0,
            0.0,
            "31/08/2006",
            ""
        )
        val house2 = House(
            0,
            2,
            2,
            450000,
            120,
            5,
            "Grande maison de campagne isolée",
            "Mayenne",
            0.0,
            0.0,
            "12/10/2012",
            ""
        )
        val house3 = House(
            0,
            3,
            3,
            800000,
            50,
            2,
            "Appartement en plein centre de Paris",
            "Paris",
            0.0,
            0.0,
            "08/01/2018",
            ""
        )

        houseDatabase.insertNewHouse(house1)
        houseDatabase.insertNewHouse(house2)
        houseDatabase.insertNewHouse(house3)


        val getHouseTest = houseDatabase.getHouseTypeAgents()

        val houseFromDatabase1 =
            getHouseTest.first { it.house.description == "Petite maison de bourg" }
        val houseFromDatabase2 =
            getHouseTest.first { it.house.description == "Grande maison de campagne isolée" }
        val houseFromDatabase3 =
            getHouseTest.first { it.house.description == "Appartement en plein centre de Paris" }

        assertEquals(3, getHouseTest.size)
        // House ID
        assertEquals(1, houseFromDatabase1.house.houseId)
        assertEquals(2, houseFromDatabase2.house.houseId)
        assertEquals(3, houseFromDatabase3.house.houseId)
        // Price
        assertEquals(100000, houseFromDatabase1.house.price)
        assertEquals(450000, houseFromDatabase2.house.price)
        assertEquals(800000, houseFromDatabase3.house.price)
        // Description
        assertEquals("Petite maison de bourg", houseFromDatabase1.house.description)
        assertEquals("Grande maison de campagne isolée", houseFromDatabase2.house.description)
        assertEquals("Appartement en plein centre de Paris", houseFromDatabase3.house.description)
        // Surface
        assertEquals(60, houseFromDatabase1.house.surface)
        assertEquals(120, houseFromDatabase2.house.surface)
        assertEquals(50, houseFromDatabase3.house.surface)
        // Number of room
        assertEquals(3, houseFromDatabase1.house.roomNumber)
        assertEquals(5, houseFromDatabase2.house.roomNumber)
        assertEquals(2, houseFromDatabase3.house.roomNumber)
        // Location
        assertEquals("sainte-jamme", houseFromDatabase1.house.location)
        assertEquals("Mayenne", houseFromDatabase2.house.location)
        assertEquals("Paris", houseFromDatabase3.house.location)
        // Entry date
        assertEquals("31/08/2006", houseFromDatabase1.house.entryDate)
        assertEquals("12/10/2012", houseFromDatabase2.house.entryDate)
        assertEquals("08/01/2018", houseFromDatabase3.house.entryDate)

        // INTEREST POINTS

        val interestPoints1 = HouseAndInterestPoints(houseFromDatabase1.house.houseId, 1)
        val interestPoints2 = HouseAndInterestPoints(houseFromDatabase2.house.houseId, 2)
        val interestPoints3 = HouseAndInterestPoints(houseFromDatabase3.house.houseId, 3)

        val interestList = arrayListOf<HouseAndInterestPoints>().apply {
            add(interestPoints1)
            add(interestPoints2)
            add(interestPoints3)
        }

        houseDatabase.insertListInterestPoints(interestList)

        val getHouseInterestPoints = houseDatabase.getHouseTypeAgents()

        val houseInterestFromDatabase1 =
            getHouseInterestPoints.first { it.house.description == "Petite maison de bourg" }
        val houseInterestFromDatabase2 =
            getHouseInterestPoints.first { it.house.description == "Grande maison de campagne isolée" }
        val houseInterestFromDatabase3 =
            getHouseInterestPoints.first { it.house.description == "Appartement en plein centre de Paris" }

        assertEquals(1, houseInterestFromDatabase1.interestPoints[0].interestId)
        assertEquals(2, houseInterestFromDatabase2.interestPoints[0].interestId)
        assertEquals(3, houseInterestFromDatabase3.interestPoints[0].interestId)

//        // PICTURE

        val picture1 = Pictures(0, "aabb", "salon", houseFromDatabase1.house.houseId)
        val picture2 = Pictures(0, "ccdd", "cuisine", houseFromDatabase2.house.houseId)
        val picture3 = Pictures(0, "eeff", "chambre", houseFromDatabase3.house.houseId)

        val picturesList = arrayListOf<Pictures>().apply {
            add(picture1)
            add(picture2)
            add(picture3)
        }

        houseDatabase.insertPictures(picturesList)

        val getPictures1 = houseDatabase.getPictures(houseFromDatabase1.house.houseId)
        val getPictures2 = houseDatabase.getPictures(houseFromDatabase2.house.houseId)
        val getPictures3 = houseDatabase.getPictures(houseFromDatabase3.house.houseId)

        assertEquals("aabb", getPictures1[0].pictures)
        assertEquals("ccdd", getPictures2[0].pictures)
        assertEquals("eeff", getPictures3[0].pictures)

        // UPDATE

        val house = House(
            houseFromDatabase1.house.houseId,
            1,
            1,
            100000,
            80,
            3,
            "GRANDE MAISON DE CAMPAGNE",
            "sainte-jamme",
            0.0,
            0.0,
            "31/08/2006",
            ""
        )

        houseDatabase.updateHouse(house)

        val getUpdatedHouse = houseDatabase.getHouseTypeAgents()

        val houseUpdateFromDatabase =
            getUpdatedHouse.first { it.house.description == "GRANDE MAISON DE CAMPAGNE" }

        assertEquals("GRANDE MAISON DE CAMPAGNE", houseUpdateFromDatabase.house.description)
    }
}
