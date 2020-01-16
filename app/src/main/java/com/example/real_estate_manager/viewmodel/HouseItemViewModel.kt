package com.example.real_estate_manager.viewmodel

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.model.HouseTypeAgent

data class HouseItemViewModel(val houseTypeAgent: HouseTypeAgent) {

    @StringRes
    fun getAvailability(): Int =
        if(houseTypeAgent.house.soldDate == null) R.string.available else R.string.unavailable

    @ColorRes
    fun getAvailabilityColor(): Int =
        if(houseTypeAgent.house.soldDate == null) R.color.green else R.color.red
}
