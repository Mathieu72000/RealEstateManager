package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.HouseCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDetailsViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    val houseTypeAgent = MutableLiveData<HouseCrossRef?>().apply {
        postValue(null)
    }

    fun getHouseTypeAgentDetails(houseId: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseTypeAgent.postValue(getHouseDatabase?.getHouseTypeAgent(houseId))
        }
    }

    val availability= Transformations.map(houseTypeAgent) {
        application.getString(if(it?.house?.soldDate == null) R.string.available else R.string.unavailable)
    }
}