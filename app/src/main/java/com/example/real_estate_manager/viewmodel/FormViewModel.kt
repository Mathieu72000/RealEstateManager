package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.*

class FormViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    fun addHouse(house: House) {
        getHouseDatabase?.insertHouse(house)
    }

    fun addInterestPoints(interestPoints: InterestPoints) {
        getHouseDatabase?.insertInterestPoints(interestPoints)
    }

    fun addRealEstateAgents(realEstateAgent: RealEstateAgent) {
        getHouseDatabase?.insertEstateAgents(realEstateAgent)
    }

    fun addType(type: Type) {
        getHouseDatabase?.insertType(type)
    }

    // -----------------------------------------------------------------
    val formSurface = MutableLiveData<String>()
    // -------------------------------------------
    val formRoomNumber = MutableLiveData<String>()
    // -------------------------------------------
    val formPrice = MutableLiveData<String>()
    // -------------------------------------------
    val formInterestPoints = MutableLiveData<String>()
    // -------------------------------------------
    val formDescription = MutableLiveData<String>()
    // -------------------------------------------
    val formEntryDate = MutableLiveData<String>()
    // -------------------------------------------
    val formSoldDate = MutableLiveData<String>()
}


