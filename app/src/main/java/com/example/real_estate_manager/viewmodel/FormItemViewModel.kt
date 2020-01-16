package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.real_estate_manager.R
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.HouseTypeAgent
import com.example.real_estate_manager.room.model.InterestPoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// The ViewModel class role is to provide data to the UI and survive configuration changes (It act as a communication center between the Repository and the UI)
class FormItemViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    // -----------------------------------------------------------------

    // HouseTypeAgent
    private val houseTypeAgentList = MutableLiveData<List<HouseTypeAgent>>()

    val itemList = Transformations.map(houseTypeAgentList) { house -> house.map { HouseItem(HouseItemViewModel(it)) } }

    fun getHouseTypeAgent() {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseTypeAgentList.postValue(getHouseDatabase?.getHouseTypeAgents())
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

val displayPrice = MutableLiveData<String>()
val displayLocation = MutableLiveData<String>()
val displayType = MutableLiveData<String>()
val displaySurface = MutableLiveData<String>()
val displayState = MutableLiveData<String>()
val displayDescription = MutableLiveData<String>()
val displayRoomNumber = MutableLiveData<String>()
val displayInterestPoints = MutableLiveData<String>()
val displayEntryDate = MutableLiveData<String>()
val displayAgent = MutableLiveData<String>()
}