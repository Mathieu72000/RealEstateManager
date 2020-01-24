package com.example.real_estate_manager.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ImageViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormBinding
import com.example.real_estate_manager.room.model.House
import com.example.real_estate_manager.viewmodel.FormViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.chip.Chip
import com.myhexaville.smartimagepicker.ImagePicker
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
                this.lifecycleOwner = this@FormFragment
                this.viewmodel = formViewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.configureAutoComplete()

        this.configureChips()
    }

    // AutocompleteListener
    private fun configureAutoComplete() {

        (childFragmentManager.findFragmentById(R.id.form_autocomplete_fragment) as? AutocompleteSupportFragment)?.apply {

            setPlaceFields(
                listOf(
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS
                )
            )
            setTypeFilter(TypeFilter.ADDRESS)
            setHint("Location")

            setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                }

                override fun onError(status: Status) {
                    Toast.makeText(context, status.statusMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun configureChips() {
        val listOfType = listOf("House", "Flat", "Penthouse", "Villa", "Duplex")
        for (type: String in listOfType) {
            val chip = Chip(context).apply {
                text = type
            }
            form_chipGroup.addView(chip)
        }
        form_chipGroup.setOnCheckedChangeListener { group, checkedId ->

        }
    }
}

