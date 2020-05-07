package com.example.real_estate_manager.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.MainActivity
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentSearchBinding
import com.example.real_estate_manager.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import java.io.Serializable


class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        ).apply {
            this.lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            this.item = searchViewModel
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.getLoadData()
        onClickSearch()
    }

    private fun onClickSearch() {

        search_submit_button.setOnClickListener {

            search_interestChips.children.filter {
                it is Chip && it.isChecked
            }.map {
                it.tag as Long
            }.toList().let {
                searchViewModel.interestPointsId.value = it
            }

            search_Type.children.filter {
                it is Chip && it.isChecked
            }.map {
                it.tag as Long
            }.toList().let {
                searchViewModel.typeId.value = it
            }

            searchViewModel.search()

            searchViewModel.houseIdList.observe(viewLifecycleOwner, Observer {
                startActivity(Intent(context, MainActivity::class.java).apply {
                    putExtra(Constants.SEARCH_RESULT_ID, it as? ArrayList<Long>)
                    putExtra(Constants.IS_SEARCH_CONTEXT, true)
                })
            })
        }
    }
}
