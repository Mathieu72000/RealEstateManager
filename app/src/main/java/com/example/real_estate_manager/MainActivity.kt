package com.example.real_estate_manager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.real_estate_manager.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setSupportActionBar(main_activity_Toolbar)

        val mainFragment = MainFragment.newInstance(intent.getSerializableExtra(Constants.SEARCH_RESULT_ID) as? ArrayList<Long>)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeHolder, mainFragment)
            .commitAllowingStateLoss()
    }
}
