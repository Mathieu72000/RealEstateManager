package com.example.real_estate_manager

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.real_estate_manager.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        this.configureToolbar()

        val searchFragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.search_fragment_placeHolder, searchFragment)
            .commitAllowingStateLoss()
    }

    private fun configureToolbar() {
        setSupportActionBar(search_toolbar)
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
