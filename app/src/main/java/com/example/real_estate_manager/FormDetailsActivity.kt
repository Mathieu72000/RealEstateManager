package com.example.real_estate_manager

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.real_estate_manager.fragment.FormDetailsFragment
import kotlinx.android.synthetic.main.activity_form_details.*

class FormDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_form_details)

        this.configureToolbar()

        val detailsFragment =
            FormDetailsFragment.newInstance(intent.getLongExtra(Constants.HOUSE_ID, 0))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details_fragment_placeHolder, detailsFragment)
            .commitAllowingStateLoss()

    }

    // ------------ Configuration ---------------
    private fun configureToolbar() {
        setSupportActionBar(form_details_toolbar)
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
