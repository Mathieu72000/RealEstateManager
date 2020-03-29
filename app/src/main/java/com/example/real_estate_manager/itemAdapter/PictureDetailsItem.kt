package com.example.real_estate_manager.itemAdapter

import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentPictureDetailsItemBinding
import com.example.real_estate_manager.room.model.Pictures
import com.xwray.groupie.databinding.BindableItem

class PictureDetailsItem(private val item: Pictures) :
    BindableItem<FragmentPictureDetailsItemBinding>() {

    override fun getLayout() = R.layout.fragment_picture_details_item

    override fun bind(viewBinding: FragmentPictureDetailsItemBinding, position: Int) {
        viewBinding.item = item
    }
}