package com.example.real_estate_manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.R
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.viewmodel.HouseViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel by viewModels<HouseViewModel>()

    fun newInstance(): MainFragment {
        return MainFragment()
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
    viewModel.itemList.observe(this, Observer
    {
        updateRecyclerView(it)
    })
        viewModel.getHouses()
}

    // Configure RecyclerView and GroupieAdapter and glue it together
    private fun updateRecyclerView(items: List<HouseItem>) {
      groupAdapter.update(items)
    }
}