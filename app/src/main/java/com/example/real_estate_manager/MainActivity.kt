package com.example.real_estate_manager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.real_estate_manager.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setSupportActionBar(main_Toolbar)

        val mainFragment = MainFragment().newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeHolder, mainFragment)
            .commitAllowingStateLoss()
        }

    // ---------------- MENU --------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add -> {
                val intent = Intent(applicationContext, FormActivity::class.java)
                startActivity(intent)
            }
            R.id.search -> {
                val intent = Intent(applicationContext, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
