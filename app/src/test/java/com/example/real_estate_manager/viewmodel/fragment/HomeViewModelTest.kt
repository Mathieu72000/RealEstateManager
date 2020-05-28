package com.example.real_estate_manager.viewmodel.fragment

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.real_estate_manager.room.model.*
import com.example.real_estate_manager.viewmodel.HomeViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private val application by lazy { ApplicationProvider.getApplicationContext<Application>() }

    private val homeViewModel by lazy { HomeViewModel(application) }

    @Test
    fun isLiveDataEmitting() {

        val house =
            House(0, 0, 0, 390000, 80, 5, "Grande maison", "paris", 0.0, 0.0, "21/09/2019", "")
        val type = Type(0, "House")
        val agent = RealEstateAgent(0, "Alexi")
        val interestPoints = InterestPoints(0, "Hopital")
        val interestList = arrayListOf<InterestPoints>().apply {
            add(interestPoints)
        }
        val picture = Pictures(0, "aabb", "salon", 0)
        val pictureList = arrayListOf<Pictures>().apply {
            add(picture)
        }
        homeViewModel.houseCrossRefList.postValue(
            listOf(
                HouseCrossRef(
                    house,
                    type,
                    agent,
                    interestList,
                    pictureList
                )
            )
        )

        Assert.assertEquals(1, homeViewModel.itemList.getOrAwaitValue().size)
        Assert.assertEquals("House", homeViewModel.houseCrossRefList.value?.get(0)?.type?.type)
        Assert.assertEquals("Alexi", homeViewModel.houseCrossRefList.value?.get(0)?.realEstateAgent?.realEstateAgent)
        Assert.assertEquals("Hopital", homeViewModel.houseCrossRefList.value?.get(0)?.interestPoints?.get(0)?.interestPoints)
        Assert.assertEquals("aabb", homeViewModel.houseCrossRefList.value?.get(0)?.pictures?.get(0)?.pictures)
        Assert.assertEquals("salon", homeViewModel.houseCrossRefList.value?.get(0)?.pictures?.get(0)?.pictureText)
    }
}