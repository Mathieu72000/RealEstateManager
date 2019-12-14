package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// The ViewModel class role is to provide data to the UI and survive configuration changes (It act as a communication center between the Repository and the UI)
class HouseViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    // -----------------------------------------------------------------

    // House
    private val houseList = MutableLiveData<List<House>>()

    val itemList = Transformations.map(houseList) { house -> house.map { HouseItem(it) } }

    fun getHouses() {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseList.postValue(getHouseDatabase?.getAllHouses())
        }
    }
    // -----------------------------------------------------------------

    // InterestPoints
    private val interestPointsList = MutableLiveData<List<InterestPoints>>()

    fun getInterestPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            interestPointsList.postValue(getHouseDatabase?.getAllInterestPoints())
        }
    }
    // -----------------------------------------------------------------

    // RealEstateAgent
    private val estateAgentsList = MutableLiveData<List<RealEstateAgent>>()

    fun getEstateAgents() {
        viewModelScope.launch(Dispatchers.IO) {
            estateAgentsList.postValue(getHouseDatabase?.getAllAgents())
        }
    }
    // -----------------------------------------------------------------

    // Type
    private val type = MutableLiveData<Type>()

    fun getType() {
        viewModelScope.launch(Dispatchers.IO) {
            type.postValue(getHouseDatabase?.getType())
        }
    }
}