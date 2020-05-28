package com.example.real_estate_manager

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import utils.Utils

@RunWith(AndroidJUnit4::class)
class UtilsTest {

    private val context by lazy { ApplicationProvider.getApplicationContext<Application>() }

    @Test
    fun testIfConnectionIsAvailable(){
        Assert.assertEquals(true, Utils.checkIfInternetIsAvailable(context))
    }
}