package com.retar.go4lunch.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retar.go4lunch.R
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.ui.list.adapter.RestaurantAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : DaggerFragment(), ListView {

    @Inject
    lateinit var presenter: ListViewPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.onActivityCreated()
    }

    companion object {
        const val TAG = "com.retar.go4lunch.ui.list.listfragment"
        @JvmStatic
        fun newInstance() = ListFragment()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun loadData(data: List<RestaurantEntity>) {
        recyclerView.adapter = RestaurantAdapter(data){
            presenter.onListItemClick(it)
        }
    }

}
