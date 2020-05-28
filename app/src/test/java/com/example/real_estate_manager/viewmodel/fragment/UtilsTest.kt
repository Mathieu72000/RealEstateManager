package com.example.real_estate_manager.viewmodel.fragment

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import utils.Utils
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class UtilsTest {

    @Test
    fun testEuroConversion() {
        Assert.assertEquals(55, Utils.convertEuroToDollars(50))
    }

    @Test
    fun testReformatDate() {
        val currentDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formatted = simpleDateFormat.format(currentDate)
        Assert.assertEquals(formatted, Utils.reformatTodayDate())
    }
}