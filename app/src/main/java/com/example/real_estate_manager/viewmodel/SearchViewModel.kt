package com.example.real_estate_manager.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.real_estate_manager.MainActivity
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val getHouseDatabase = HouseDatabase.getInstance(application)

    private val context = getApplication<Application>()

    val interestPointsList = MutableLiveData<List<InterestPoints>>().apply {
        postValue(null)
    }

    val typeList = MutableLiveData<List<Type>>().apply {
        postValue(null)
    }

    fun getLoadData() {
        viewModelScope.launch(Dispatchers.IO) {
            interestPointsList.postValue(getHouseDatabase?.getAllInterestPoints())
            typeList.postValue(getHouseDatabase?.getType())
        }
    }

    fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            var isWhereOperatorAlreadyUsed = false
            val stringBuilder =
                StringBuilder("SELECT House.houseId FROM House INNER JOIN Type ON House.houseTypeId = Type.typeId INNER JOIN HouseAndInterestPoints ON HouseAndInterestPoints.houseId = House.houseId ")

            if (minPrice.value?.toIntOrNull() != null) {
                stringBuilder.append("WHERE House.price >= ${minPrice.value?.toIntOrNull() ?: 0} ")
                isWhereOperatorAlreadyUsed = true
            }
            if (maxPrice.value?.toIntOrNull() != null) {
                stringBuilder.append(
                    if (isWhereOperatorAlreadyUsed == true) {
                        " AND "
                    } else {
                        isWhereOperatorAlreadyUsed = true
                        " WHERE "
                    }
                )
                stringBuilder.append("House.price <= ${maxPrice.value?.toIntOrNull() ?: 0} ")
            }

            if (minSurface.value?.toIntOrNull() != null) {
                stringBuilder.append("WHERE House.surface >= ${minSurface.value?.toIntOrNull() ?: 0} ")
                isWhereOperatorAlreadyUsed = true
            }
            if (maxSurface.value?.toIntOrNull() != null) {
                stringBuilder.append(
                    if (isWhereOperatorAlreadyUsed == true) {
                        " AND "
                    } else {
                        isWhereOperatorAlreadyUsed = true
                        " WHERE "
                    }
                )
                stringBuilder.append("House.surface <= ${maxSurface.value?.toIntOrNull() ?: 0} ")
            }
            if (interestPointsId.value?.isNotEmpty() != false) {
                stringBuilder.append(
                    if (isWhereOperatorAlreadyUsed == true) {
                        " AND "
                    } else {
                        isWhereOperatorAlreadyUsed = true
                        " WHERE "
                    }
                )
                stringBuilder.append(
                    "InterestPoints.interestId IN (${interestPointsId.value?.joinToString(
                        separator = ","
                    ) { it.toString() }})"
                )
            }
            if (typeId.value?.isNotEmpty() != false) {
                stringBuilder.append(
                    if (isWhereOperatorAlreadyUsed == true) {
                        " AND "
                    } else {
                        " WHERE "
                    }
                )
                stringBuilder.append("Type.typeId IN (${typeId.value?.joinToString(separator = ",") { it.toString() }}")
            }
            getHouseDatabase?.getHouses(stringBuilder.toString())

            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    val minPrice = MutableLiveData<String>()
    val maxPrice = MutableLiveData<String>()
    val minSurface = MutableLiveData<String>()
    val maxSurface = MutableLiveData<String>()
    val interestPointsId = MutableLiveData<List<Long>>()
    val typeId = MutableLiveData<List<Long>>()
}