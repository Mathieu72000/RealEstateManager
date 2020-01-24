package com.example.real_estate_manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormDetailsBinding
import com.example.real_estate_manager.viewmodel.FormDetailsViewModel

class FormDetailsFragment : Fragment() {

    private val viewModel by viewModels<FormDetailsViewModel>()

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

        viewModel.getHouseTypeAgentDetails(houseId)
    }
}
