package com.example.real_estate_manager.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.FormActivity
import com.example.real_estate_manager.R
import com.example.real_estate_manager.SearchActivity
import com.example.real_estate_manager.itemAdapter.HouseItem
import com.example.real_estate_manager.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel by viewModels<HomeViewModel>()

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragment_RecyclerView?.adapter = groupAdapter
        bindUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> FormActivity::class.java
            R.id.search -> SearchActivity::class.java
            else -> null
        }?.let {
            startActivityForResult(Intent(context, it), Constants.RESULT_REQUEST_CODE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.RESULT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            viewModel.getHouseTypeAgent()
        }
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