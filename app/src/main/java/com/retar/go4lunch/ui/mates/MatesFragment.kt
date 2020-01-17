package com.retar.go4lunch.ui.mates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.retar.go4lunch.R

class MatesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mates, container, false)
    }

    companion object {

        const val TAG = "com.retar.go4lunch.ui.mates.matesfragment"

        @JvmStatic
        fun newInstance() = MatesFragment()
    }
}
