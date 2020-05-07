package com.example.real_estate_manager.itemAdapter

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentMainItemBinding
import com.example.real_estate_manager.viewmodel.HouseItemViewModel
import com.xwray.groupie.databinding.BindableItem

class HouseItem(private val item: HouseItemViewModel) :
    BindableItem<FragmentMainItemBinding>(item.houseCrossRef.house.houseId) {

    override fun getLayout() = R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item
        val intent = Intent().apply {
            action = Constants.HOUSEID_BROADCAST
            putExtra(Constants.HOUSE_ID, item.houseCrossRef.house.houseId)
        }
        viewBinding.root.setOnClickListener {
            LocalBroadcastManager.getInstance(it.context).sendBroadcast(intent)
        }
    }
}
