package com.retar.go4lunch.ui.mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.retar.go4lunch.R
import com.retar.go4lunch.base.TabAdapter
import com.retar.go4lunch.ui.map.MapViewPresenter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : DaggerFragment(), MainView {

    @Inject
    lateinit var presenter: MainViewPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onActivityCreated()

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

        const val OFFSCREEN_PAGES = 2

    }

    override fun toRestaurantDetail(id: String) {
        Toast.makeText(context, "heheh", Toast.LENGTH_LONG).show()
    }

    override fun setUpLayout() {
        viewPager.adapter =
            TabAdapter(childFragmentManager, requireContext())
        viewPager.offscreenPageLimit =
            OFFSCREEN_PAGES

        tab_layout.setupWithViewPager(viewPager)
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_map)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_list)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_mates)    }
}
