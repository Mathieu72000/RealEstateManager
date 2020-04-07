package com.example.real_estate_manager.itemAdapter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.util.Base64
import com.bumptech.glide.Glide
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentPictureItemBinding
import com.example.real_estate_manager.viewmodel.FormPictureViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xwray.groupie.databinding.BindableItem

class PictureItem(private val item: FormPictureViewModel) :
    BindableItem<FragmentPictureItemBinding>(
        System.identityHashCode(item.base64).toLong()
    ) {

    override fun getLayout() = R.layout.fragment_picture_item

    @SuppressLint("PrivateResource")
    override fun bind(viewBinding: FragmentPictureItemBinding, position: Int) {
        if (item.base64 != viewBinding.pictureItem.tag) {
        Glide.with(viewBinding.root.context).load(Base64.decode(item.base64, Base64.DEFAULT))
            .into(viewBinding.pictureItem)
            viewBinding.pictureItem.tag = item.base64
        }
        viewBinding.item = item
        val broadcast: Intent = Intent().apply {
            action = Constants.PICTURE_INTENT_FILTER
            putExtra(Constants.PICTURE_POSITION, position)
        }
        viewBinding.root.context?.let { ctx ->
            viewBinding.root.setOnLongClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> ctx.sendBroadcast(broadcast)
                        DialogInterface.BUTTON_NEGATIVE -> null
                    }
                }
                MaterialAlertDialogBuilder(
                    ctx,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage(R.string.delete_picture)
                    setPositiveButton(R.string.yes, dialogClickListener)
                    setNegativeButton(R.string.no, dialogClickListener)
                    create().show()
                }
                it.showContextMenu()
            }
        }
    }
}