package com.example.real_estate_manager

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.real_estate_manager.fragment.FormFragment
import com.example.real_estate_manager.room.model.HouseCrossRef
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        // ------------- Toolbar ----------------
        this.configureToolbar()

        val formFragment = FormFragment.newInstance(intent.getLongExtra("house", 0))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.form_fragment_placeHolder, formFragment)
            .commitAllowingStateLoss()
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
}