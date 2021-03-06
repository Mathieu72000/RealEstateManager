package com.example.real_estate_manager.fragment


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentFormBinding
import com.example.real_estate_manager.itemAdapter.PictureItem
import com.example.real_estate_manager.itemAdapter.SpinnerAdapter
import com.example.real_estate_manager.room.model.RealEstateAgent
import com.example.real_estate_manager.room.model.Type
import com.example.real_estate_manager.viewmodel.FormViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val formViewModel by viewModels<FormViewModel>()
    private lateinit var easyImage: EasyImage
    private lateinit var receiver: BroadcastReceiver

    companion object {
        fun newInstance(house: Long?): FormFragment {
            val formFragment = FormFragment()
            if (house != null) {
                val bundle = Bundle()
                bundle.putLong(Constants.HOUSE, house)
                formFragment.arguments = bundle
            }
            return formFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureOnReceived()
        val intentFilter = IntentFilter(Constants.PICTURE_INTENT_FILTER)
        context?.registerReceiver(receiver, intentFilter)
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

        val houseId = arguments?.getLong(Constants.HOUSE, 0)
        if (houseId != null) {
            formViewModel.getLoadData(houseId)
        }

        this.configurePermissions()
        this.configurePlaceAutoComplete()
        this.configureEntryDatePicker()
        this.configureSoldDatePicker()
        this.getTypeId()
        this.getRealEstateAgentsId()
        this.configureEasyImage()
        form_picture_recyclerView?.adapter = groupAdapter
        this.bindUi(houseId)
        this.saveHouse()
    }

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

    private fun configurePermissions() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            form_take_photo_button.isEnabled = true
            form_upload_photo_button.isEnabled = true
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.PICTURE_REQUEST_CODE
            )
        }
    }

    private fun configureEasyImage() {
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(true)
            .setFolderName("Real Estate Picture")
            .setCopyImagesToPublicGalleryFolder(true)
            .build()

        form_upload_photo_button.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                easyImage.openGallery(this@FormFragment)
                v.clearFocus()
            }
        }

        form_take_photo_button.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                easyImage.openCameraForImage(this@FormFragment)
                v.clearFocus()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place: Place? = data?.let { Autocomplete.getPlaceFromIntent(it) }
            formViewModel.formLocation.postValue(place?.address)
            formViewModel.longitude = place?.latLng?.longitude
            formViewModel.latitude = place?.latLng?.latitude
        }

        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    formViewModel.addPhoto(imageFiles.toList())
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    super.onImagePickerError(error, source)
                    error.printStackTrace()
                }

                override fun onCanceled(source: MediaSource) {
                    super.onCanceled(source)
                }
            })

    }

    private fun configureEntryDatePicker() {
        val calendar = Calendar.getInstance()

        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfDay, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfDay)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dayFormat = getString(R.string.dayFormat)
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

                val dayFormat = getString(R.string.dayFormat)
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
                formViewModel.formTypeId.postValue(parent?.getItemIdAtPosition(position))
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
                    formViewModel.formRealEstateAgentsId.postValue(
                        parent?.getItemIdAtPosition(position)
                    )
                }
            }
    }

    private fun bindUi(houseId: Long?) {
        formViewModel.itemList.observe(viewLifecycleOwner, Observer
        {
            updateRecyclerView(it)
        })
        if (houseId != null) {
            restoreRealEstateAgent()
            restoreType()
            restoreInterestPoints()
        }
    }

    private fun updateRecyclerView(items: List<PictureItem>) {
        groupAdapter.update(items)
    }

    private fun restoreRealEstateAgent() {
        formViewModel.formRealEstateAgentsId.observe(viewLifecycleOwner, Observer { realEstateId ->
            (form_real_estate_spinner.adapter as? SpinnerAdapter<RealEstateAgent>)?.let {
                val indexOfFirst = it.item.indexOfFirst {
                    it.agentId == realEstateId
                }
                if (indexOfFirst != -1) {
                    form_real_estate_spinner.setSelection(indexOfFirst)
                }
            }
        })
    }

    private fun restoreType() {
        formViewModel.formTypeId.observe(viewLifecycleOwner, Observer { typeId ->
            (form_type_spinner.adapter as? SpinnerAdapter<Type>)?.let {
                val indexOfFirst = it.item.indexOfFirst {
                    it.typeId == typeId
                }
                if (indexOfFirst != -1) {
                    form_type_spinner.setSelection(indexOfFirst)
                }
            }
        })
    }

    private fun restoreInterestPoints() {
        formViewModel.formInterestPointsId.observe(viewLifecycleOwner, Observer { interestPoints ->
            form_interestPoints.children.filter {
                it is Chip
            }.forEach {
                if (interestPoints.contains(it.tag as Long)) {
                    form_interestPoints.check(it.id)
                }
            }
        })
    }

    private fun saveHouse() {
        form_submit_button?.setOnClickListener {

            form_interestPoints.children.filter {
                it is Chip && it.isChecked
            }.map {
                it.tag as Long
            }.toList().let {
                formViewModel.formInterestPointsId.value = it
            }

            lifecycleScope.launch(Dispatchers.IO) {
                formViewModel.saveHouse()
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
            Toast.makeText(context, R.string.house_saved, Toast.LENGTH_LONG).show()
        }
    }

    private fun configureOnReceived() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val position = intent?.getIntExtra(Constants.PICTURE_POSITION, 0)
                if (position != null) {
                    formViewModel.removePictures(position)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)
    }
}