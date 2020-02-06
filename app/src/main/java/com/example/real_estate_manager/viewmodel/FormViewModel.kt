package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    val interestPointsList = MutableLiveData<List<InterestPoints>>().apply {
        postValue(null)
    }

    val typeList = MutableLiveData<List<Type>>().apply {
        postValue(null)
    }

    suspend fun addHouse(house: House) {
        getHouseDatabase?.insertNewHouse(house)
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

    fun getLoadData(){
        viewModelScope.launch(Dispatchers.IO) {
            interestPointsList.postValue(getHouseDatabase?.getAllInterestPoints())
        typeList.postValue(getHouseDatabase?.getType())
        }
    }

    // -----------------------------------------------------------------

    var latitude: Double? = null
    // -------------------------------------------
    var longitude: Double? = null
    // -------------------------------------------
    val formSurface = MutableLiveData<Int>()
    // -------------------------------------------
    val formRoomNumber = MutableLiveData<Int>()
    // -------------------------------------------
    val formPrice = MutableLiveData<Int>()
    // --------------------------------------------
    val formInterestPoints = MutableLiveData<String>()
    // -------------------------------------------
    val formRealEstateAgents = MutableLiveData<String>()
    // -------------------------------------------
    val formDescription = MutableLiveData<String>()
    // -------------------------------------------
    val formEntryDate = MutableLiveData<String>()
    // -------------------------------------------
    val formSoldDate = MutableLiveData<String>()
    // -------------------------------------------
    var formLocation = MutableLiveData<String>()
    // -------------------------------------------
    val formLatitude = MutableLiveData<Double>()
    // -------------------------------------------
    val formLongitude = MutableLiveData<Double>()
    // -------------------------------------------

    // mediatorlivedata
    fun saveHouse() {
        viewModelScope.launch(Dispatchers.IO) {
            val house = House(
                0,
                0,
                0,
                formPrice.value,
                formSurface.value,
                formRoomNumber.value,
                formDescription.value,
                formLocation.value,
                formLatitude.value,
                formLongitude.value,
                formEntryDate.value,
                formSoldDate.value
            )
            addHouse(house)
        }
    }

    fun saveInterestPoints() = InterestPoints(0,
        formInterestPoints.value)

    fun saveRealEstateAgents() = RealEstateAgent(0,
        formRealEstateAgents.value)



}


