package com.example.real_estate_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.real_estate_manager.fragment.FormDetailsFragment
import com.example.real_estate_manager.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val configuration by lazy {
        (application as? MyApplication)?.configuration ?: MyApplication.Configuration(resources)
    }
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setSupportActionBar(main_activity_Toolbar)

        this.configureOnReceived()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(Constants.HOUSEID_BROADCAST))

        val mainFragment =
            MainFragment.newInstance(intent.getSerializableExtra(Constants.SEARCH_RESULT_ID) as? ArrayList<Long>?, intent.getBooleanExtra(Constants.IS_SEARCH_CONTEXT, false))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeHolder, mainFragment)
            .commitAllowingStateLoss()


    }

    private fun configureOnReceived() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Constants.HOUSEID_BROADCAST) {
                    val houseId = intent.getLongExtra(Constants.HOUSE_ID, 0)
                    if (configuration.isPhone) {
                        startActivity(Intent(context, FormDetailsActivity::class.java).apply {
                            putExtra(
                                Constants.HOUSE_ID,
                                houseId
                            )
                        })
                    } else if (configuration.isTablet) {

                        val detailsFragment =
                            FormDetailsFragment.newInstance(houseId)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_placeHolder2, detailsFragment)
                            .commitAllowingStateLoss()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this@MainActivity).unregisterReceiver(receiver)
    }
}

