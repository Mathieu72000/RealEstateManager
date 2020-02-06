package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.HouseTypeAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

// The ViewModel class role is to provide data to the UI and survive configuration changes (It act as a communication center between the Repository and the UI)
class FormItemViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    // -----------------------------------------------------------------

    // HouseTypeAgent
    private val houseTypeAgentList = MutableLiveData<List<HouseTypeAgent>>()

    val itemList = Transformations.map(houseTypeAgentList) { house ->
        house.map {
            HouseItem(
                HouseItemViewModel(it)
            )
        }
    }

    fun getHouseTypeAgent() {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseTypeAgentList.postValue(getHouseDatabase?.getHouseTypeAgents())
        }
    }
}