package com.example.real_estate_manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoanViewModel(application: Application) : AndroidViewModel(application) {


    val loanPrice = MutableLiveData<String>()

    val loanDeposit = MutableLiveData<String>()

    val loanPeriod = MutableLiveData<String>()

    val loanInterestRate = MutableLiveData<String>()

    val loanResult = MutableLiveData<Double>()

    fun calculateLoan() {
        loanResult.postValue(
            loanPrice.value?.toDoubleOrNull()
                ?.minus(loanDeposit.value?.toDoubleOrNull() ?: 0.0)
                ?.times(loanInterestRate.value?.toDoubleOrNull() ?: 0.0)
                ?.div(loanPeriod.value?.toDoubleOrNull()?.times(12) ?: 1.0)
        )

    }

}