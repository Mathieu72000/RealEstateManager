package com.example.real_estate_manager.binding

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.model.InterestPoints
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

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