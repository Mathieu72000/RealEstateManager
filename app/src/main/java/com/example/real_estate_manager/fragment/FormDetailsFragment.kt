package com.example.real_estate_manager.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.FormActivity
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormDetailsBinding
import com.example.real_estate_manager.itemAdapter.PictureDetailsItem
import com.example.real_estate_manager.viewmodel.FormDetailsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_form_details.*
import timber.log.Timber

class FormDetailsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<FormDetailsViewModel>()
    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var houseId: Long = 0
    private lateinit var mMap: GoogleMap

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
        setHasOptionsMenu(true)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.description_maps) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseId = arguments?.getLong(Constants.HOUSE_ID) ?: 0
        viewModel.getHouseCrossRefDetails(houseId)
        details_picture_recyclerView?.adapter = groupAdapter
        bindUI()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val latitude: Double? = viewModel.houseTypeAgent.value?.house?.latitude
        val longitude: Double? = viewModel.houseTypeAgent.value?.house?.longitude
        if (googleMap != null) {
            mMap = googleMap
            if(latitude != null && longitude != null) {
                val housePosition = LatLng(latitude, longitude)
                mMap.addMarker(MarkerOptions().position(housePosition))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(housePosition, 16.0F))
            }
        }
    }

    private fun bindUI() {
        viewModel.housePictures.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it?.map {
                PictureDetailsItem(it)
            })
        })
    }

    private fun updateRecyclerView(item: List<PictureDetailsItem>?) {
        item?.let { groupAdapter.update(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_modify_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.modify -> FormActivity::class.java
            else -> null
        }?.let {
            startActivity(Intent(context, it).apply {
                putExtra("house", viewModel.houseTypeAgent.value?.house?.houseId)
            })
        }
        return super.onOptionsItemSelected(item)
    }
}
