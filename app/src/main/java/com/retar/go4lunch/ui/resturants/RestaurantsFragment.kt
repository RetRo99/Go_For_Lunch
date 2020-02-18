package com.retar.go4lunch.ui.resturants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.retar.go4lunch.R
import com.retar.go4lunch.base.BaseAutoCompleteFragment
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.ui.resturants.adapter.RestaurantAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class RestaurantsFragment : BaseAutoCompleteFragment(), RestaurantsView {

    @Inject
    lateinit var presenter: RestaurantsViewPresenter

    private lateinit var adapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun showToast(stringResource: Int) {
        Toast.makeText(context, stringResource, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RestaurantAdapter() {
            presenter.onListItemClick(
                it.id,
                it.name
            )
        }

        recyclerView.adapter = adapter

        autoSearch.doOnTextChanged { text, _, _, _ ->
            presenter.onSearchChanged(text)
        }

        presenter.onActivityCreated()
    }

    companion object {
        const val TAG = "com.retar.go4lunch.ui.list.listfragment"
        @JvmStatic
        fun newInstance() = RestaurantsFragment()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setData(data: List<RestaurantEntity>) {
        adapter.setData(data)
    }
}