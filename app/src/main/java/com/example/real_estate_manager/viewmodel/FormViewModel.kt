package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type

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
    val surfaceEditText = MutableLiveData<String>()
    // -------------------------------------------
    val roomEditText = MutableLiveData<String>()
    // -------------------------------------------
    val priceEditText = MutableLiveData<String>()
    // -------------------------------------------
    val interestEditText = MutableLiveData<String>()
    // -------------------------------------------
    val descriptionEditText = MutableLiveData<String>()
    // -------------------------------------------
    val entryDateEditText = MutableLiveData<String>()
    // -------------------------------------------
    val soldDateEditText = MutableLiveData<String>()

}


