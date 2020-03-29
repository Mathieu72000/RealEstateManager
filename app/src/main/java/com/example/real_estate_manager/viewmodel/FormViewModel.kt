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

    private suspend fun updateHouse(house: House): Int =
        getHouseDatabase?.updateHouse(house) ?: -1

    private suspend fun addInterestPoints(interestPoints: List<HouseAndInterestPoints>) {
        getHouseDatabase?.insertListInterestPoints(interestPoints)
    }

    private suspend fun addPictures(pictures: List<Pictures>) {
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

    private val formPictures = MutableLiveData<MutableList<FormPictureViewModel>>().apply {
        postValue(mutableListOf())
    }

    fun getLoadData(houseId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            interestPointsList.postValue(getHouseDatabase?.getAllInterestPoints())
            typeList.postValue(getHouseDatabase?.getType())
            realEstateAgentsList.postValue(getHouseDatabase?.getAllAgents())
            val house = getHouseDatabase?.getHouseTypeAgent(houseId)
            if (house != null) {
                formLocation.postValue(house.house.location)
                formSurface.postValue(house.house.surface.toString())
                formRoomNumber.postValue(house.house.roomNumber.toString())
                formPrice.postValue(house.house.price.toString())
//                formInterestPointsId.postValue(house.interestPoints.map { it.interestId })
//                formTypeId.postValue(formTypeId.value)
                formDescription.postValue(house.house.description)
                formRealEstateAgentsId.postValue(house.realEstateAgent.agentId)
                formEntryDate.postValue(house.house.entryDate)
                formSoldDate.postValue(house.house.soldDate)
//                formPictures.postValue()
                latitude = house.house.latitude
                longitude = house.house.longitude
            }
        }
    }

    var houseId: Long? = null

    // -------------------------------------------
    var latitude: Double? = null

    // -------------------------------------------
    var longitude: Double? = null

    // -------------------------------------------
    val formSurface = MutableLiveData<String>()

    // -------------------------------------------
    val formRoomNumber = MutableLiveData<String>()

    // -------------------------------------------
    val formPrice = MutableLiveData<String>()

    // -------------------------------------------
    val formInterestPointsId = MutableLiveData<List<Long>>()

    // ------------------------------------------
    val formTypeId = MutableLiveData<Long>()

    // ------------------------------------------
    val formRealEstateAgentsId = MutableLiveData<Long>()

    // ------------------------------------------
    val formDescription = MutableLiveData<String>()

    // ------------------------------------------
    val formEntryDate = MutableLiveData<String>()

    // ------------------------------------------
    val formSoldDate = MutableLiveData<String>()

    // ------------------------------------------
    val formLocation = MutableLiveData<String>()

    // ------------------------------------------

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
        addSource(formPictures) {
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
                && formPictures.value?.isNotEmpty() ?: false
    }

    suspend fun saveHouse() {
        if (houseId == null) {
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
                    val bitmap = BitmapFactory.decodeFile(it.mediaFile.file.path)
                    val byteArray = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                    val toByteArray = byteArray.toByteArray()
                    val base64 = Base64.encodeToString(toByteArray, Base64.DEFAULT)
                    Pictures(0, base64, it.text.value, houseId)
                }?.let { addPictures(it) }
            }
        } else {
            updateHouse()
        }
    }

    suspend fun updateHouse() {
        val house = House(
            houseId ?: 0,
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
        updateHouse(house)
        getHouseDatabase?.deleteInterestPoints(houseId ?: 0)
        formInterestPointsId.value?.map {
            HouseAndInterestPoints(houseId ?: 0, it)
        }?.let {
            addInterestPoints(it)
        }
        getHouseDatabase?.deletePictures(houseId ?: 0)
        formPictures.value?.map {
            val bitmap = BitmapFactory.decodeFile(it.mediaFile.file.path)
            val byteArray = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
            val toByteArray = byteArray.toByteArray()
            val base64 = Base64.encodeToString(toByteArray, Base64.DEFAULT)
            Pictures(0, base64, it.text.value, houseId ?: 0)
        }?.let { addPictures(it) }

    }

    val itemList = Transformations.map(formPictures) { picture ->
        picture.map {
            PictureItem(
                FormPictureViewModel(it.mediaFile, it.text)
            )
        }
    }

    fun addPhoto(photo: List<MediaFile>) {
        formPictures.postValue(formPictures.value?.union(photo.map {
            FormPictureViewModel(
                it,
                MutableLiveData("")
            )
        })?.toMutableList())
    }

    fun removePictures(position: Int) {
        formPictures.value?.removeAt(position)
        formPictures.postValue(formPictures.value)
    }
}
