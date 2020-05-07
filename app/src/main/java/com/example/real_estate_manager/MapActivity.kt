package com.example.real_estate_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.real_estate_manager.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(map_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mapFragment = MapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.map_placeholder, mapFragment)
            .commitAllowingStateLoss()
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
