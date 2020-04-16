package com.example.real_estate_manager.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.real_estate_manager.R
import com.example.real_estate_manager.databinding.FragmentSearchBinding
import com.example.real_estate_manager.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*


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

    private fun onClickSearch(){
        search_submit_button.setOnClickListener {
            searchViewModel.search()
        }
    }
}
