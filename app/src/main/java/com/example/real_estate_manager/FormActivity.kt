package com.example.real_estate_manager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_form.*
import java.util.*


class FormActivity : AppCompatActivity(){

    private var entryDateListener: OnDateSetListener? = null
    private var soldDateListener: OnDateSetListener? = null
    private lateinit var startDate: TextInputEditText
    private lateinit var soldDate: TextInputEditText
    private lateinit var spinner: Spinner

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_form)

        startDate = form_entry_date_editText
        soldDate = form_sold_date_editText
        spinner = form_real_estate_spinner

        // ------------- Toolbar ----------------
        this.configureToolbar()

        // -------------- Data ------------------
        val surface = form_surface_editText.text
        val roomNumber = form_number_of_room_editText.text
        val price = form_price_editText.text
        val type = form_type_editText.text
        val description = form_description_editText.text
        this.configureDatePicker()


        // -------- AutocompleteTextView ------------
        this.configureAutoCompleteTextView()

        // --- SPINNER ---

        val test_list = arrayOf("Jean Bonneau", "Axel dugarry", "marcel proust")

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, test_list)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
    }

    // ------------ Configuration ---------------
    private fun configureToolbar(){
        setSupportActionBar(form_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureDatePicker(){

        // ---------- Entry DatePicker ----------
        startDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_MinWidth, entryDateListener, year, month, day)
            dpd.show()
        }

        entryDateListener = OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            var month1 = month
            month1++
            var selectedDay = dayOfMonth.toString()
            var selectedMonth = month.toString()

            if (month1 < 10) {
                selectedMonth = "0$month1"
            }
            if (dayOfMonth < 10) {
                selectedDay = "0$dayOfMonth"
            }
            val date = "$selectedDay/$selectedMonth/$year"
            startDate.setText(date)
        }

        // ------------ Sold DatePicker ------------
        val soldDate = form_sold_date_editText

        soldDate.setOnClickListener {
            val calendar2 = Calendar.getInstance()
            val year2 = calendar2.get(Calendar.YEAR)
            val month2 = calendar2.get(Calendar.MONTH)
            val day2 = calendar2.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_MinWidth, soldDateListener, year2, month2, day2)
            dpd.show()
        }

        soldDateListener = OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            var month1 = month
            var selectedDay = dayOfMonth.toString()
            month1++
            var selectedMonth = month1.toString()


            if (month1 < 10) {
                selectedMonth = "0$month1"
            }
            if (dayOfMonth < 10) {
                selectedDay = "0$dayOfMonth"
            }
            val date = "$selectedDay/$selectedMonth/$year"
            soldDate.setText(date)
        }
    }

    private fun configureAutoCompletePredictions(s: Editable) {
        val placesClient: PlacesClient = Places.createClient(this)
        val token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()

        val request: FindAutocompletePredictionsRequest =
            FindAutocompletePredictionsRequest.builder()
                .setQuery(s.toString())
                .setSessionToken(token)
                .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener {
            val houseList = arrayListOf<String>()
            for(predictions in it.autocompletePredictions){
                houseList.add(predictions.getPrimaryText(null).toString())
            }

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, houseList)

            form_autocompleteTextView.setAdapter(adapter)

        }
    }

    private fun configureAutoCompleteTextView(){

        form_autocompleteTextView.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if(s!!.isNotEmpty()){
                    configureAutoCompletePredictions(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    // -------- Back arrow --------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}