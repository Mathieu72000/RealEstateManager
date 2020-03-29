package com.example.real_estate_manager.binding

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.real_estate_manager.R
import com.example.real_estate_manager.room.model.InterestPoints
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type
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
    if (entries != null) {
        val arrayAdapter =
            object : ArrayAdapter<Type?>(context, android.R.layout.simple_spinner_item, entries) {
                override fun getItemId(position: Int): Long {
                    return entries[position].typeId
                }
            }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("entriesAgents")
fun Spinner.setAgentsEntries(entries: List<RealEstateAgent>?) {
    if (entries != null) {
        val arrayAdapter = object :
            ArrayAdapter<RealEstateAgent?>(context, android.R.layout.simple_spinner_item, entries) {
            override fun getItemId(position: Int): Long {
                return entries[position].agentId
            }
        }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("srcBase64")
fun ImageView.setSrcBase64(entries: String?) {
    val decodeBase64 = Base64.decode(entries, Base64.DEFAULT)
    val decodeByteArray = BitmapFactory.decodeByteArray(decodeBase64, 0, decodeBase64.size)
    setImageBitmap(decodeByteArray)

}
