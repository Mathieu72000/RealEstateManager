package com.example.real_estate_manager.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.*
import com.example.real_estate_manager.itemAdapter.PictureItem
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.MediaFile
import java.io.ByteArrayOutputStream

class FormViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance
    private val getHouseDatabase = HouseDatabase.getInstance(application)

    private suspend fun addHouse(house: House): Long =
        getHouseDatabase?.insertNewHouse(house) ?: -1

    suspend fun addInterestPoints(interestPoints: List<HouseAndInterestPoints>) {
        getHouseDatabase?.insertListInterestPoints(interestPoints)
    }

    suspend fun addPictures(pictures: List<Pictures>) {
        getHouseDatabase?.insertPictures(pictures)
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
    val formInterestPointsId = MutableLiveData<List<Long>>()

    // -------------------------------------------
    val formTypeId = MutableLiveData<Long>()

    // -------------------------------------------
    val formRealEstateAgentsId = MutableLiveData<Long>()

    // -------------------------------------------
    val formDescription = MutableLiveData<String>()

    // -------------------------------------------
    val formEntryDate = MutableLiveData<String>()

    // -------------------------------------------
    val formSoldDate = MutableLiveData<String>()

    // -------------------------------------------
    val formLocation = MutableLiveData<String>()

    // -------------------------------------------
    val formPictures = MutableLiveData<MutableList<MediaFile>>().apply {
        postValue(mutableListOf())
    }

    val formPicturesText = MutableLiveData<String>()

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
        addSource(formRoomNumber) {
            postValue(isHouseValid())
        }
        addSource(formDescription) {
            postValue(isHouseValid())
        }
        addSource(formEntryDate) {
            postValue(isHouseValid())
        }
    }

    private fun isHouseValid(): Boolean {
        return formPrice.value?.toIntOrNull() ?: 0 > 0
                && formSurface.value?.toIntOrNull() ?: 0 > 0
                && formRoomNumber.value?.toIntOrNull() ?: 0 > 0
                && formDescription.value != null
                && formLocation.value != null
                && formEntryDate.value != null
    }

    fun saveHouse() {
        viewModelScope.launch(Dispatchers.IO) {
            val house = House(
                0,
                formRealEstateAgentsId.value,
                formTypeId.value,
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
            val houseId = addHouse(house)
            if (houseId != -1L) {
                formInterestPointsId.value?.map {
                    HouseAndInterestPoints(houseId, it)
                }?.let {
                    addInterestPoints(it)
                }

                formPictures.value?.map {
                    val bitmap = BitmapFactory.decodeFile(it.file.path)
                    val byteArray = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                    val toByteArray = byteArray.toByteArray()
                    val base64 = Base64.encodeToString(toByteArray, Base64.DEFAULT)
                }
            }
        }
    }

    val itemList = Transformations.map(formPictures) { picture ->
        picture.map {
            PictureItem(
                PictureViewModel(it))
        }
    }

    fun addPhoto(photo: List<MediaFile>) {
        formPictures.postValue(formPictures.value?.union(photo)?.toMutableList())
    }
}
