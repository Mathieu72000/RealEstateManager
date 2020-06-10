package com.example.real_estate_manager.viewmodel.fragment

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.real_estate_manager.viewmodel.SearchViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModel {

    private val context by lazy { ApplicationProvider.getApplicationContext<Application>() }


    private val searchViewModel1 by lazy { SearchViewModel(context) }
    private val searchViewModel2 by lazy { SearchViewModel(context) }
    private val searchViewModel3 by lazy { SearchViewModel(context) }
    private val searchViewModel4 by lazy { SearchViewModel(context) }
    private val searchViewModel5 by lazy { SearchViewModel(context) }


    @Test
    fun testSearchStringBuilder() {

        searchViewModel1.numberOfRooms.postValue("5")
        searchViewModel2.numberOfRooms.postValue("1")
        searchViewModel3.numberOfRooms.postValue("3")
        searchViewModel4.numberOfRooms.postValue("4")


        searchViewModel1.minPrice.postValue("0")
        searchViewModel3.minPrice.postValue("80000")
        searchViewModel4.minPrice.postValue("100000")
        searchViewModel5.minPrice.postValue("30000")

        searchViewModel1.maxPrice.postValue("100000")
        searchViewModel2.maxPrice.postValue("50000")
        searchViewModel3.maxPrice.postValue("300000")
        searchViewModel4.maxPrice.postValue("900000")

        searchViewModel1.minSurface.postValue("20")
        searchViewModel2.minSurface.postValue("80")
        searchViewModel3.minSurface.postValue("100")
        searchViewModel4.minSurface.postValue("200")

        searchViewModel1.maxSurface.postValue("300")
        searchViewModel3.maxSurface.postValue("200")
        searchViewModel4.maxSurface.postValue("350")

        searchViewModel1.interestPointsId.postValue(listOf(0, 1, 3))
        searchViewModel2.interestPointsId.postValue(listOf(2, 0, 1))
        searchViewModel3.interestPointsId.postValue(listOf(3, 2, 1))
        searchViewModel4.interestPointsId.postValue(listOf(1, 0, 3))

        searchViewModel1.typeId.postValue(listOf(1, 0))
        searchViewModel2.typeId.postValue(listOf(3, 2))
        searchViewModel3.typeId.postValue(listOf(2, 1))
        searchViewModel4.typeId.postValue(listOf(3, 4))

        Assert.assertEquals(
            "SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId WHERE House.price >= 0  AND House.price <= 100000  AND House.surface >= 20  AND House.surface <= 300  AND House.roomNumber == 5 AND HouseAndInterestPoints.interestId IN (0,1,3) AND Type.typeId IN (1,0)"
            , searchViewModel1.buildSearch()
        )
        Assert.assertEquals(
            "SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId  WHERE House.price <= 50000  AND House.surface >= 80  AND House.roomNumber == 1 AND HouseAndInterestPoints.interestId IN (2,0,1) AND Type.typeId IN (3,2)",
            searchViewModel2.buildSearch()
        )
        Assert.assertEquals(
            "SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId WHERE House.price >= 80000  AND House.price <= 300000  AND House.surface >= 100  AND House.surface <= 200  AND House.roomNumber == 3 AND HouseAndInterestPoints.interestId IN (3,2,1) AND Type.typeId IN (2,1)",
            searchViewModel3.buildSearch()
        )
        Assert.assertEquals(
            "SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId WHERE House.price >= 100000  AND House.price <= 900000  AND House.surface >= 200  AND House.surface <= 350  AND House.roomNumber == 4 AND HouseAndInterestPoints.interestId IN (1,0,3) AND Type.typeId IN (3,4)",
            searchViewModel4.buildSearch()
        )
        Assert.assertEquals(
            "SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId WHERE House.price >= 30000 ",
            searchViewModel5.buildSearch()
        )
    }
}