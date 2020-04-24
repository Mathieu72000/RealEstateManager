package com.example.real_estate_manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentLoanBinding
import com.example.real_estate_manager.viewmodel.LoanViewModel
import kotlinx.android.synthetic.main.fragment_loan.*

class LoanFragment : Fragment() {

    private val loanViewModel by viewModels<LoanViewModel>()

    companion object {
        fun newInstance(): LoanFragment {
            return LoanFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoanBinding>(
            inflater,
            R.layout.fragment_loan,
            container,
            false
        ).apply {
            this.lifecycleOwner = this@LoanFragment.viewLifecycleOwner
            this.item = loanViewModel
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loan_result_button?.setOnClickListener {
            loanViewModel.calculateLoan()
        }
    }
}
