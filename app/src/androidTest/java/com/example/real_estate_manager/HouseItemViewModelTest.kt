package com.example.real_estate_manager

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.real_estate_manager.room.model.*
import com.example.real_estate_manager.viewmodel.HouseItemViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HouseItemViewModelTest {

    private val house1 =
        House(0, 0, 0, 390000, 80, 5, "Grande maison", "paris", 0.0, 0.0, "21/09/2019", null)
    private val type1 = Type(0, "House")
    private val agent1 = RealEstateAgent(0, "Alexi")
    private val interestPoints1 = InterestPoints(0, "Hopital")
    private val interestList1 = arrayListOf<InterestPoints>().apply {
        add(interestPoints1)
    }
    private val picture1 = Pictures(0, "aabb", "salon", 0)
    private val pictureList1 = arrayListOf<Pictures>().apply {
        add(picture1)
    }

    private val house2 =
        House(
            0,
            1,
            1,
            70000,
            40,
            2,
            "Petit appartement",
            "sainte-jamme",
            0.0,
            0.0,
            "10/10/2008",
            "30/01/2009"
        )
    private val type2 = Type(0, "Flat")
    private val agent2 = RealEstateAgent(0, "Alexandre")
    private val interestPoints2 = InterestPoints(0, "Pharmacy")
    private val interestList2 = arrayListOf<InterestPoints>().apply {
        add(interestPoints2)
    }
    private val picture2 = Pictures(0, "ccdd", "kitchen", 0)
    private val pictureList2 = arrayListOf<Pictures>().apply {
        add(picture2)
    }

    private val houseItemViewModel1 by lazy {
        HouseItemViewModel(
            HouseCrossRef(
                house1,
                type1,
                agent1,
                interestList1,
                pictureList1
            )
        )
    }
    private val houseItemViewModel2 by lazy {
        HouseItemViewModel(
            HouseCrossRef(
                house2,
                type2,
                agent2,
                interestList2,
                pictureList2
            )
        )
    }

    @Test
    fun testStringAvailability() {

        Assert.assertEquals(R.string.available, houseItemViewModel1.getAvailability())

        Assert.assertEquals(R.string.unavailable, houseItemViewModel2.getAvailability())
    }

    @Test
    fun testColorAvailability() {

        Assert.assertEquals(R.color.green, houseItemViewModel1.getAvailabilityColor())

        Assert.assertEquals(R.color.red, houseItemViewModel2.getAvailabilityColor())
    }


    @Test
    fun testNumberFormat() {
        Assert.assertEquals("€390,000", houseItemViewModel1.formatNumber())
    }
}