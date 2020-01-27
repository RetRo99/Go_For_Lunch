package com.retar.go4lunch.ui.holderfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.holderfragment.adapter.TabAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class HolderFragment : DaggerFragment(), HolderView {

    @Inject
    lateinit var presenter: HolderViewPresenter

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
        fun newInstance() = HolderFragment()

        const val OFFSCREEN_PAGES = 2

    }


    override fun setUpLayout() {
        viewPager.adapter =
            TabAdapter(
                childFragmentManager,
                requireContext()
            )
        viewPager.offscreenPageLimit =
            OFFSCREEN_PAGES

        tab_layout.setupWithViewPager(viewPager)
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_map)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_list)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_mates)
    }
}
