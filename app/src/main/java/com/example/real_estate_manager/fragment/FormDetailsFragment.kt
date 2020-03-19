package com.example.real_estate_manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormDetailsBinding
import com.example.real_estate_manager.itemAdapter.PictureDetailsItem
import com.example.real_estate_manager.viewmodel.FormDetailsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_form_details.*

class FormDetailsFragment : Fragment() {

    private val viewModel by viewModels<FormDetailsViewModel>()
    private var groupAdapter = GroupAdapter<GroupieViewHolder>()

    companion object {
        fun newInstance(houseId: Long): FormDetailsFragment {
            return FormDetailsFragment().apply {
                arguments = bundleOf(Constants.HOUSE_ID to houseId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFormDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_form_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val houseId = arguments?.getLong(Constants.HOUSE_ID) ?: 0

        viewModel.getHouseCrossRefDetails(houseId)
        details_picture_recyclerView?.adapter = groupAdapter
        bindUI()
    }

    private fun bindUI(){
        viewModel.housePictures.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it?.map {
                PictureDetailsItem(it)
            })
        })
    }

    private fun updateRecyclerView(item: List<PictureDetailsItem>?){
        item?.let { groupAdapter.update(it) }
    }
}
