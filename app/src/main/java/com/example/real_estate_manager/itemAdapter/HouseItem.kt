package com.example.real_estate_manager.itemAdapter

import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.ActivityMainItemBinding
import com.example.real_estate_manager.room.model.House
import com.xwray.groupie.databinding.BindableItem

class HouseItem(private val item: House): BindableItem<ActivityMainItemBinding>(item.houseId.toLong()) {

    override fun getLayout() = R.layout.activity_main_item

    override fun bind(viewBinding: ActivityMainItemBinding, position: Int) {
        viewBinding.item = item
        viewBinding.root.setOnClickListener {}
    }
}
