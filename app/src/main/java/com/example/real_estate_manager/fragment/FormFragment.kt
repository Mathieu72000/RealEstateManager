package com.example.real_estate_manager.fragment


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.util.*

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
        this.configureEntryDatePicker()
        this.configureSoldDatePicker()
        formViewModel.getLoadData()
        this.getTypeId()
        this.getRealEstateAgentsId()

//        form_submit_button?.setOnClickListener {
//            formViewModel.saveHouse(
//                formViewModel.formTypeId.value,
//                formViewModel.formRealEstateAgentsId.value,
//                )
//        }
    }


    // -------- Place AutoComplete ----------

    private fun configurePlaceAutoComplete() {
        form_autocomplete_textView.setOnClickListener {
            if (form_autocomplete_textView != null) form_autocomplete_textView.text = null
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

    private fun configureEntryDatePicker() {
        val calendar = Calendar.getInstance()

        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfDay, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfDay)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dayFormat = "dd.MM.yyyy"
                val simpleDateFormat = SimpleDateFormat(dayFormat, Locale.FRANCE)
                form_entry_date_textView.text = simpleDateFormat.format(calendar.time)
                formViewModel.formEntryDate.postValue(simpleDateFormat.format(calendar.time))
            }

        form_entry_date_textView?.setOnClickListener {
            if (form_entry_date_textView != null) form_entry_date_textView.text = null
            DatePickerDialog(
                requireContext(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun configureSoldDatePicker() {
        val calendar = Calendar.getInstance()

        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfDay, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfDay)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dayFormat = "dd.MM.yyyy"
                val simpleDateFormat = SimpleDateFormat(dayFormat, Locale.FRANCE)
                form_sold_date_textView.text = simpleDateFormat.format(calendar.time)
                formViewModel.formSoldDate.postValue(simpleDateFormat.format(calendar.time))
            }

        form_sold_date_textView?.setOnClickListener {
            if (form_sold_date_textView != null) form_sold_date_textView.text = null
            DatePickerDialog(
                requireContext(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun getTypeId() {
        form_type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    formViewModel.formTypeId.value = parent.getItemIdAtPosition(position)
                }
            }
        }
    }

    private fun getRealEstateAgentsId() {
        form_real_estate_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent != null) {
                        formViewModel.formRealEstateAgentsId.value =
                            parent.getItemIdAtPosition(position)
                    }
                }
            }
    }
}

