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
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.autoSearch
import kotlinx.android.synthetic.main.fragment_map_view.*
import javax.inject.Inject

class RestaurantsFragment : BaseAutoCompleteFragment(), RestaurantsView {

    @Inject
    lateinit var presenter: RestaurantsViewPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        autoSearch.doOnTextChanged { text, _, _, _ ->
            presenter.onSearchChanged(text)
        }

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

    override fun toast(it: String?) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }

    override fun setData(data: List<RestaurantEntity>, firstItem: Int) {

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = RestaurantAdapter(data) {
            presenter.onListItemClick(
                it.id,
                it.name,
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            )
        }

        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(firstItem)
    }
}