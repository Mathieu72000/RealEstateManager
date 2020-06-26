package com.example.real_estate_manager.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.FormDetailsActivity
import com.example.real_estate_manager.MapActivity
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentMapBinding
import com.example.real_estate_manager.viewmodel.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import utils.Utils

class MapFragment : Fragment(), OnMapReadyCallback, OnInfoWindowClickListener {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = viewModel
        if (Utils.checkIfInternetIsAvailable(context)) {
            val mapFragment: SupportMapFragment? =
                childFragmentManager.findFragmentById(R.id.maps) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
        } else {
            Toast.makeText(context, "No connexion", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constants.REQUEST_LOCATION_CODE
            )
        } else {
            Timber.i("Permission granted")
            if (googleMap != null) {
                googleMap.isMyLocationEnabled = true
                viewModel.getDataForMap()
                viewModel.houseCrossRefList.observe(viewLifecycleOwner, Observer {
                    fusedLocationClient.lastLocation.addOnSuccessListener { userLocation ->
                        it.filter {
                            val houseLocation = Location("").apply {
                                latitude = it.house.latitude!!
                                longitude = it.house.longitude!!
                            }
                            userLocation.distanceTo(houseLocation) <= 10000
                        }.map {
                            val housePosition = LatLng(it.house.latitude!!, it.house.longitude!!)
                            val marker: Marker = googleMap.addMarker(
                                MarkerOptions().position(housePosition)
                                    .title(it.type.type)
                                    .snippet(it.house.location)
                            )
                            marker.tag = it.house.houseId
                        }
                    }
                })
                googleMap.setOnInfoWindowClickListener(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_LOCATION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(context, MapActivity::class.java))
                }
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker?) {
        startActivity(Intent(context, FormDetailsActivity::class.java).apply {
            putExtra(Constants.HOUSE_ID, marker?.tag as? Long)
        })
    }
}
