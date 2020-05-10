package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.HouseCrossRef
import com.example.real_estate_manager.room.model.Pictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDetailsViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    val houseTypeAgent = MutableLiveData<HouseCrossRef?>().apply {
        postValue(null)
    }

    val houseId = MutableLiveData<Long>()

    val housePictures = MutableLiveData<List<Pictures>>().apply { postValue(null) }

    fun getHouseCrossRefDetails(houseId: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            houseTypeAgent.postValue(getHouseDatabase?.getHouseTypeAgent(houseId))
            housePictures.postValue(getHouseDatabase?.getPictures(houseId))
        }
    }
}