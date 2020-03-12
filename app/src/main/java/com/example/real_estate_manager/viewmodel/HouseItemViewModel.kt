package com.example.real_estate_manager.viewmodel

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.model.HouseCrossRef
import java.text.NumberFormat
import java.util.*

data class HouseItemViewModel(val houseCrossRef: HouseCrossRef) {

    @StringRes
    fun getAvailability(): Int =
        if(houseCrossRef.house.soldDate == null) R.string.available else R.string.unavailable

    @ColorRes
    fun getAvailabilityColor(): Int =
        if(houseCrossRef.house.soldDate == null) R.color.green else R.color.red


    fun formatNumber(): String{
        val format = NumberFormat.getCurrencyInstance().apply {
            currency = Currency.getInstance("EUR")
            minimumFractionDigits = 0
        }
        return format.format(houseCrossRef.house.price)
    }
}
