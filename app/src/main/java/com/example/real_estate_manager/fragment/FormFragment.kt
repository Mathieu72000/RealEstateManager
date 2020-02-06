package com.example.real_estate_manager.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormBinding
import com.example.real_estate_manager.viewmodel.FormViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_form.*

class FormFragment : Fragment() {

    private val formViewModel by viewModels<FormViewModel>()

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFormBinding>(
            inflater,
            R.layout.fragment_form,
            container,
            false
        )
            .apply {
                this.lifecycleOwner = this@FormFragment.viewLifecycleOwner
                this.viewmodel = formViewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.configurePlaceAutoComplete()

        form_submit_button?.setOnClickListener {
            formViewModel.saveHouse()
        }

        formViewModel.getLoadData()
    }

    // -------- Place AutoComplete ----------

    private fun configurePlaceAutoComplete() {
        form_autocomplete_textView.setOnClickListener {
            val fields: List<Place.Field> = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent: Intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(requireContext())
            startActivityForResult(intent, Constants.AUTOCOMPLETE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place: Place? = data?.let { Autocomplete.getPlaceFromIntent(it) }
            formViewModel.formLocation.postValue(place?.address)
            formViewModel.longitude = place?.latLng?.longitude
            formViewModel.latitude = place?.latLng?.latitude
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // --------- CHIPS TEST -----------

    private fun configureChips() {
        val listOfType = listOf("House", "Flat", "Penthouse", "Villa", "Duplex")
        for (type: String in listOfType) {
            val chip = Chip(context).apply {
                text = type
                isCheckable = true
                isClickable = true
            }
            form_type.addView(chip)
        }
    }

    private fun configureChipsinterest() {
        val listOfType =
            listOf("School", "Highschool", "Restaurant", "Hospital", "Pharmacy", "Supermarket")
        for (type: String in listOfType) {
            val chip = Chip(context).apply {
                text = type
            }
            form_interestPoints.addView(chip)
        }
        form_interestPoints.setOnCheckedChangeListener { group, checkedId ->

        }
    }
}

