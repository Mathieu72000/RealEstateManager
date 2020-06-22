package com.example.real_estate_manager

import android.app.Application
import android.content.ContentResolver
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.real_estate_manager.provider.RealEstateManagerContentProvider
import com.example.real_estate_manager.room.database.HouseDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RealEstateManagerContentProviderTest {

    private val context by lazy { ApplicationProvider.getApplicationContext<Application>() }

    private lateinit var contentResolver: ContentResolver

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(context, HouseDatabase::class.java).allowMainThreadQueries()
            .build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun testHouseQuery() {
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.HOUSE_ID,
            RealEstateManagerContentProvider.HOUSE_DESCRIPTION,
            RealEstateManagerContentProvider.HOUSE_ENTRY_DATE,
            RealEstateManagerContentProvider.HOUSE_SOLD_DATE,
            RealEstateManagerContentProvider.HOUSE_NUMBER_OF_ROOM,
            RealEstateManagerContentProvider.HOUSE_PRICE,
            RealEstateManagerContentProvider.HOUSE_SURFACE,
            RealEstateManagerContentProvider.HOUSE_LATITUDE,
            RealEstateManagerContentProvider.HOUSE_LONGITUDE,
            RealEstateManagerContentProvider.HOUSE_LOCATION
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_HOUSE,
            providerTest,
            null,
            null,
            null
        )
        Assert.assertEquals(
            0,
            contentResolver?.getColumnIndex(RealEstateManagerContentProvider.HOUSE_ID)
        )
        Assert.assertEquals(
            1,
            contentResolver?.getColumnIndex(RealEstateManagerContentProvider.HOUSE_DESCRIPTION)
        )
        contentResolver?.moveToFirst()
        Assert.assertEquals(contentResolver?.columnCount, 10)
        Assert.assertEquals(contentResolver?.count, 1)
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_ID
                )
            ), 1L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_DESCRIPTION
                )
            ), "Beautiful flat, close to the university of Le Mans"
        )
        Assert.assertEquals(
            contentResolver?.getInt(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_SURFACE
                )
            ), 80
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_LOCATION
                )
            ), "6 Rue du manoir, 72000 Le Mans, France"
        )
        Assert.assertEquals(
            contentResolver?.getInt(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_PRICE
                )
            ), 100000
        )
        Assert.assertEquals(
            contentResolver?.getInt(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.HOUSE_NUMBER_OF_ROOM
                )
            ), 3
        )
        contentResolver?.close()
    }

    @Test
    fun testTypeQuery() {
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.TYPE_ID,
            RealEstateManagerContentProvider.TYPE_
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_TYPE,
            providerTest,
            null,
            null,
            null
        )

        contentResolver?.moveToFirst()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_ID
                )
            ), 1L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_
                )
            ), "House"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_ID
                )
            ), 2L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_
                )
            ), "Flat"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_ID
                )
            ), 3L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_
                )
            ), "Duplex"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_ID
                )
            ), 4L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.TYPE_
                )
            ), "Villa"
        )

    }

    @Test
    fun testInterestPointsQuery() {
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.INTEREST_POINTS_ID,
            RealEstateManagerContentProvider.INTEREST_POINTS
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_INTEREST_POINTS,
            providerTest,
            null,
            null,
            null
        )

        contentResolver?.moveToFirst()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 1L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "School"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 2L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Highschool"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 3L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Restaurant"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 4L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Hospital"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 5L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "ATM"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 6L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Pharmacy"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 7L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Supermarket"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 8L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Monument"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 9L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Church"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 10L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "Mosque"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS_ID
                )
            ), 11L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.INTEREST_POINTS
                )
            ), "TownHall"
        )
    }

    @Test
    fun testRealEstateAgentQuery() {
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.REAL_ESTATE_AGENT_ID,
            RealEstateManagerContentProvider.REAL_ESTATE_AGENT
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_REAL_ESTATE_AGENT,
            providerTest,
            null,
            null,
            null
        )

        contentResolver?.moveToFirst()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT_ID
                )
            ), 1L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT
                )
            ), "Patrick Moulin"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT_ID
                )
            ), 2L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT
                )
            ), "Ludovic Roland"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT_ID
                )
            ), 3L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT
                )
            ), "Mathieu Corroy"
        )
        contentResolver?.moveToNext()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT_ID
                )
            ), 4L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.REAL_ESTATE_AGENT
                )
            ), "Benoît Hayung"
        )
    }

    @Test
    fun testPictureQuery() {
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.PICTURES_ID,
            RealEstateManagerContentProvider.PICTURES,
            RealEstateManagerContentProvider.PICTURES_TEXT
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_PICTURES,
            providerTest,
            null,
            null,
            null
        )

        contentResolver?.moveToFirst()
        Assert.assertEquals(
            contentResolver?.getLong(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.PICTURES_ID
                )
            ), 1L
        )
        Assert.assertEquals(
            contentResolver?.getString(
                contentResolver.getColumnIndex(
                    RealEstateManagerContentProvider.PICTURES_TEXT
                )
            ), "Living room"
        )
    }

    @Test
    fun testWhereQuery(){
        val providerTest: Array<String> = arrayOf(
            RealEstateManagerContentProvider.HOUSE_ID
        )

        val contentResolver = contentResolver.query(
            RealEstateManagerContentProvider.URI_HOUSE,
            providerTest,
            "${RealEstateManagerContentProvider.HOUSE_SURFACE} >= ?",
            arrayOf("80"),
            null
        )

        Assert.assertEquals(contentResolver?.count, 1)
    }
}