package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.HouseTypeAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDetailsViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    val houseTypeAgent = MutableLiveData<HouseTypeAgent>()
    val description = Transformations.map(houseTypeAgent){it.house.description}

    fun getHouseTypeAgent(houseId: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseTypeAgent.postValue(getHouseDatabase?.getHouseTypeAgent(houseId))
        }
    }
}