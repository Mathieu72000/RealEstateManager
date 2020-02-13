package com.example.real_estate_manager.binding

import android.content.res.ColorStateList
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import utils.SpinnerExtensions.ItemSelectedListener
import utils.SpinnerExtensions.setSpinnerEntries
import utils.SpinnerExtensions.setSpinnerItemSelectedListener
import utils.SpinnerExtensions.setSpinnerValue

@BindingAdapter("customChipInterest")
fun ChipGroup.setCustomChip(interestPoints: List<InterestPoints>?) {
    interestPoints?.forEach {
        this.addView(Chip(this.context).apply {
            chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.chip_purple))
            isClickable = false
            text = context.getString(
                context.resources.getIdentifier(
                    it.interestPoints,
                    "string",
                    context.packageName
                )
            )
        })
    }
}

@BindingAdapter("customChipFormInterest")
fun ChipGroup.setCustomChipForm(interestPoints: List<InterestPoints>?) {
    interestPoints?.forEach {
        this.addView(Chip(this.context).apply {
            chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.chip_purple))
            isClickable = true
            isCheckable = true
            tag = it.interestId
            text = context.getString(
                context.resources.getIdentifier(
                    it.interestPoints,
                    "string",
                    context.packageName
                )
            )
        })
    }
}

@BindingAdapter("entriesType")
fun Spinner.setTypeEntries(entries: List<Type>?) {
    setSpinnerEntries(entries)
}

@BindingAdapter("entriesAgents")
fun Spinner.setAgentsEntries(entries: List<RealEstateAgent>?) {
    setSpinnerEntries(entries)
}

@BindingAdapter("onItemSelected")
fun Spinner.setOnItemSelectedListener(itemSelectedListener: ItemSelectedListener?) {
    setSpinnerItemSelectedListener(itemSelectedListener)
}

@BindingAdapter("newValue")
fun Spinner.setNewValue(newValue: Any?) {
    setSpinnerValue(newValue)
}

