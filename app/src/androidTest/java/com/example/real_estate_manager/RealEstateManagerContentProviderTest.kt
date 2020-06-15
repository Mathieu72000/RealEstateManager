package com.example.real_estate_manager

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris.withAppendedId
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.real_estate_manager.provider.RealEstateManagerContentProvider
import com.example.real_estate_manager.room.database.HouseDatabase
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RealEstateManagerContentProviderTest {

    private val context by lazy { ApplicationProvider.getApplicationContext<Application>() }

    private lateinit var contentResolver: ContentResolver

    @Before
    fun setUp(){
        Room.inMemoryDatabaseBuilder(context, HouseDatabase::class.java).allowMainThreadQueries().build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getItemsWhenNoItemInserted() {
        val cursor = contentResolver.query(withAppendedId(RealEstateManagerContentProvider.URI_PROPERTY, 8), null, null, null, null)
        Assert.assertThat(cursor, notNullValue())
        Assert.assertThat(cursor?.count, `is`(0))
        cursor?.close()
    }

    @Test
    fun getItems(){
        val cursor = contentResolver.query(withAppendedId(RealEstateManagerContentProvider.URI_PROPERTY, 1), null, null, null, null)
        Assert.assertThat(cursor, notNullValue())
        Assert.assertThat(cursor?.count, `is`(1))
        Assert.assertThat(cursor?.moveToFirst(), `is`(true))
        Assert.assertEquals(cursor?.getInt(0), 1)
        cursor?.close()
    }
}