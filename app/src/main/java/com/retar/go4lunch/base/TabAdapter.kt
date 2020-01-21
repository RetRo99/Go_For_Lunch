package com.retar.go4lunch.base

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.list.ListFragment
import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.mates.MatesFragment

class TabAdapter(fm: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val TAB_TITLES = arrayOf(
        R.string.tab_label1,
        R.string.tab_label2,
        R.string.tab_label3
    )


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MapFragment.newInstance()
            1 -> return ListFragment.newInstance()
            2 -> return MatesFragment.newInstance()

        }
        Log.d("position", position.toString())
        return MatesFragment.newInstance()
    }


    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
}