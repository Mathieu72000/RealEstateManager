package com.example.real_estate_manager.itemAdapter

import com.bumptech.glide.Glide
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentPictureItemBinding
import com.xwray.groupie.databinding.BindableItem
import pl.aprilapps.easyphotopicker.MediaFile

class PictureItem(private val item: MediaFile) : BindableItem<FragmentPictureItemBinding>() {

    override fun getLayout() = R.layout.fragment_picture_item

    override fun bind(viewBinding: FragmentPictureItemBinding, position: Int) {
        Glide.with(viewBinding.root.context).load(item.file).into(viewBinding.pictureItem)
    }
}