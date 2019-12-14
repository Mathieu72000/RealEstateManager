package com.example.real_estate_manager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.real_estate_manager.databinding.ActivityFormBinding
import com.example.real_estate_manager.viewmodel.NewHouseViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_form.*


class FormActivity : AppCompatActivity() {

    private val formViewModel by viewModels<NewHouseViewModel>()

//    private var entryDateListener: OnDateSetListener? = null
//    private var soldDateListener: OnDateSetListener? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityFormBinding>(
            this, R.layout.activity_form
        ).apply {
            this.lifecycleOwner = this@FormActivity
            this.viewmodel = formViewModel
        }
        // ------------- Toolbar ----------------
        this.configureToolbar()

        this.configureAutoComplete()
    }

    // ------------ Configuration ---------------
    private fun configureToolbar() {
        setSupportActionBar(form_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // -------- Back arrow --------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // AutocompleteListener
    private fun configureAutoComplete() {
        // Implementation en kotlin ?? ↓↓
        val autocompleteSupportFragment: AutocompleteSupportFragment =
            supportFragmentManager.findFragmentById(R.id.form_autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteSupportFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ADDRESS)

        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

            }

            override fun onError(status: Status) {
                Toast.makeText(applicationContext, status.statusMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

//    private fun configureDatePicker(){
//
//        // ---------- Entry DatePicker ----------
//        form_entry_date_editText.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_MinWidth, entryDateListener, year, month, day).show()
//        }
//
//        entryDateListener = OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
//            var month1 = month
//            month1++
//            var selectedDay = dayOfMonth.toString()
//            var selectedMonth = month.toString()
//
//            if (month1 < 10) {
//                selectedMonth = "0$month1"
//            }
//            if (dayOfMonth < 10) {
//                selectedDay = "0$dayOfMonth"
//            }
//            val date = "$selectedDay/$selectedMonth/$year"
//            form_entry_date_editText.setText(date)
//        }
//
//        // ------------ Sold DatePicker ------------
//        form_sold_date_editText.setOnClickListener {
//            val calendar2 = Calendar.getInstance()
//            val year2 = calendar2.get(Calendar.YEAR)
//            val month2 = calendar2.get(Calendar.MONTH)
//            val day2 = calendar2.get(Calendar.DAY_OF_MONTH)
//
//            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_MinWidth, soldDateListener, year2, month2, day2)
//            dpd.show()
//        }
//
//        soldDateListener = OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
//            var month1 = month
//            var selectedDay = dayOfMonth.toString()
//            month1++
//            var selectedMonth = month1.toString()
//
//
//            if (month1 < 10) {
//                selectedMonth = "0$month1"
//            }
//            if (dayOfMonth < 10) {
//                selectedDay = "0$dayOfMonth"
//            }
//            val date = "$selectedDay/$selectedMonth/$year"
//            form_sold_date_editText.setText(date)
//        }
//
//        form_submit_button.setOnClickListener{
//
//        }
//    }
