package com.retar.go4lunch.base

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.retar.go4lunch.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*

abstract class BaseAutoCompleteFragment : DaggerFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.search) {
            autoSearch.visibility = View.VISIBLE
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}