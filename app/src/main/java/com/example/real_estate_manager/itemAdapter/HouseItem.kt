package com.example.real_estate_manager.itemAdapter

import android.content.Intent
import com.example.real_estate_manager.*
import com.example.real_estate_manager.databinding.FragmentMainItemBinding
import com.example.real_estate_manager.viewmodel.HouseItemViewModel
import com.xwray.groupie.databinding.BindableItem

class HouseItem(private val item: HouseItemViewModel) :
    BindableItem<FragmentMainItemBinding>(item.houseTypeAgent.house.houseId) {

    override fun getLayout() = R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item
        viewBinding.root.context?.let { ctx ->
            viewBinding.root.setOnClickListener {
                val intent = Intent(ctx, FormDetailsActivity::class.java).apply {
                    putExtra(
                        Constants.HOUSE_ID,
                        item.houseTypeAgent.house.houseId
                    )
                }
                ctx.startActivity(intent)
            }
        }
    }
}
