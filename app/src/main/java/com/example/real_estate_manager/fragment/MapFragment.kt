package com.example.real_estate_manager.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.FormDetailsActivity
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentMapBinding
import com.example.real_estate_manager.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback, OnInfoWindowClickListener {

    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = viewModel
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.maps) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        viewModel.getDataForMap()
        if (googleMap != null) {
            viewModel.houseCrossRefList.observe(viewLifecycleOwner, Observer {
                val latLngBounds = LatLngBounds.Builder()
                it.forEach {
                    if (it.house.latitude != null && it.house.longitude != null) {
                        val housePosition = LatLng(it.house.latitude, it.house.longitude)
                        val marker: Marker = googleMap.addMarker(
                            MarkerOptions().position(housePosition)
                                .title(it.type.type)
                                .snippet(it.house.location)
                        )
                        latLngBounds.include(housePosition)
                        marker.tag = it.house.houseId
                    }

                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 128))
            })
            googleMap.setOnInfoWindowClickListener(this)
        }
    }

    override fun onInfoWindowClick(marker: Marker?) {
        startActivity(Intent(context, FormDetailsActivity::class.java).apply {
            putExtra(Constants.HOUSE_ID, marker?.tag as? Long)
        })
    }
}
