package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.House

class NewHouseViewModel(application: Application): AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    fun addHouse(house: House) {
        getHouseDatabase?.insertHouse(house)
    }

    // -----------------------------------------------------------------
    val locationContent = MutableLiveData<String>()
    // -------------------------------------------
    val surfaceContent = MutableLiveData<String>()
    // -------------------------------------------
    val roomContent = MutableLiveData<String>()
    // -------------------------------------------
    val priceContent = MutableLiveData<String>()
    // -------------------------------------------
    val typeContent = MutableLiveData<String>()
    // -------------------------------------------
    val interestContent = MutableLiveData<String>()
    // -------------------------------------------
    val descriptionContent = MutableLiveData<String>()
    // -------------------------------------------
    var entryDateContent = MutableLiveData<String>()
    // -------------------------------------------
    var soldDateContent = MutableLiveData<String>()
}


