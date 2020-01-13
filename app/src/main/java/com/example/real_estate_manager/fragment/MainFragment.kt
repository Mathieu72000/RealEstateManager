package com.example.real_estate_manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.example.real_estate_manager.R
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.viewmodel.FormItemViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel by viewModels<FormItemViewModel>()

    companion object {
    fun newInstance(): MainFragment {
        return MainFragment()
    }
}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragment_RecyclerView?.adapter = groupAdapter
        bindUI()
    }

    // Get all the houses and observe the data changes -> refresh the RecyclerView
    private fun bindUI() {
        viewModel.itemList.observe(viewLifecycleOwner, Observer
        {
            updateRecyclerView(it)
        })
        viewModel.getHouseTypeAgent()
    }

    // Configure RecyclerView and GroupieAdapter and glue it together
    private fun updateRecyclerView(items: List<HouseItem>) {
        groupAdapter.update(items)
    }
}