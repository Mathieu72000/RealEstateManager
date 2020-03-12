package com.example.real_estate_manager.itemAdapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentPictureItemBinding
import com.example.real_estate_manager.viewmodel.PictureViewModel
import com.xwray.groupie.databinding.BindableItem
import timber.log.Timber

class PictureItem(private val item: PictureViewModel) : BindableItem<FragmentPictureItemBinding>() {

    override fun getLayout() = R.layout.fragment_picture_item

    override fun bind(viewBinding: FragmentPictureItemBinding, position: Int) {
        Glide.with(viewBinding.root.context).load(item.mediaFile.file).into(viewBinding.pictureItem)
        val broadcast: Intent = Intent().apply {
            action = "pictureClick"
            putExtra("position", position)
        }
        viewBinding.root.context?.let { ctx ->
            viewBinding.root.setOnLongClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> ctx.sendBroadcast(broadcast)
                        DialogInterface.BUTTON_NEGATIVE -> Timber.i("Dialog dismissed")
                    }
                }
                AlertDialog.Builder(ctx).apply {
                    setMessage("Delete this picture?")
                    setPositiveButton("Yes", dialogClickListener)
                    setNegativeButton("No", dialogClickListener)
                    create().show()
                }
                it.showContextMenu()
            }
        }
    }
}