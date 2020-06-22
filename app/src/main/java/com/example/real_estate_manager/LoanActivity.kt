package com.example.real_estate_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.real_estate_manager.fragment.LoanFragment
import kotlinx.android.synthetic.main.activity_loan.*

class LoanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)
        configureToolbar()

        val loanFragment = LoanFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.loan_fragment_placeHolder, loanFragment)
            .commitAllowingStateLoss()
    }

    private fun configureToolbar() {
        setSupportActionBar(loan_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
