package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.*
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

    suspend fun addHouse(house: House) {
        getHouseDatabase?.insertNewHouse(house)
    }

    suspend fun addInterestPoints(interestPoints: InterestPoints) {
        getHouseDatabase?.insertInterestPoints(interestPoints)
    }

    suspend fun addRealEstateAgents(realEstateAgent: RealEstateAgent) {
        getHouseDatabase?.insertEstateAgents(realEstateAgent)
    }

    suspend fun addType(type: Type) {
        getHouseDatabase?.insertType(type)
    }

    // --------------------------------------------------------------------

    val interestPointsList = MutableLiveData<List<InterestPoints>>().apply {
        postValue(null)
    }

    val typeList = MutableLiveData<List<Type>>().apply {
        postValue(null)
    }

    val realEstateAgentsList = MutableLiveData<List<RealEstateAgent>>().apply {
        postValue(null)
    }

    fun getLoadData() {
        viewModelScope.launch(Dispatchers.IO) {
            interestPointsList.postValue(getHouseDatabase?.getAllInterestPoints())
            typeList.postValue(getHouseDatabase?.getType())
            realEstateAgentsList.postValue(getHouseDatabase?.getAllAgents())
        }
    }

    // -----------------------------------------------------------------

    var latitude: Double? = null
    // -------------------------------------------
    var longitude: Double? = null
    // -------------------------------------------
    val formSurface = MutableLiveData<String>()
    // -------------------------------------------
    val formRoomNumber = MutableLiveData<String>()
    // -------------------------------------------
    val formPrice = MutableLiveData<String>()
    // --------------------------------------------
    val formInterestPoints = MutableLiveData<String>()
    // -------------------------------------------
    val formRealEstateAgents = MutableLiveData<String>()
    // -------------------------------------------
    val formType = MutableLiveData<String>()
    // -------------------------------------------
    val formDescription = MutableLiveData<String>()
    // -------------------------------------------
    val formEntryDate = MutableLiveData<String>()
    // -------------------------------------------
    val formSoldDate = MutableLiveData<String>()
    // -------------------------------------------
    val formLocation = MutableLiveData<String>()
    // -------------------------------------------
    val mediatorLiveData = MediatorLiveData<Boolean>().apply {
        addSource(formPrice) {
            postValue(isHouseValid())
        }
        addSource(formLocation) {
            postValue(isHouseValid())
        }
        addSource(formSurface) {
            postValue(isHouseValid())
        }
        addSource(formRoomNumber){
            postValue(isHouseValid())
        }
        addSource(formDescription){
            postValue(isHouseValid())
        }
    }

    private fun isHouseValid(): Boolean {
        return (formPrice.value?.toIntOrNull() ?: 0 ) > 0
    }

    fun saveHouse(typeId: Long, realEstateAgentId: Long, interestPointsId: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            val house = House(
                0,
                0,
                0,
                formPrice.value?.toIntOrNull() ?: 0,
                formSurface.value?.toIntOrNull() ?: 0,
                formRoomNumber.value?.toIntOrNull() ?: 0,
                formDescription.value,
                formLocation.value,
                latitude,
                longitude,
                formEntryDate.value,
                formSoldDate.value
            )
            addHouse(house)
        }
    }
}


