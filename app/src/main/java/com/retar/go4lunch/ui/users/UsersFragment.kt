package com.retar.go4lunch.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.users.adapter.UserAdapter
import com.retar.go4lunch.base.model.User
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class UsersFragment : DaggerFragment(), UsersView {

    @Inject
    lateinit var presenter: UsersViewPresenter
    private lateinit var adapter:UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = UserAdapter(listOf())
        recyclerView.adapter = adapter

        presenter.onActivityCreated()
    }

    companion object {

        const val TAG = "com.retar.go4lunch.ui.mates.matesfragment"

        @JvmStatic
        fun newInstance() = UsersFragment()
    }

    override fun setData(data: List<User>) {
        adapter.update(data)
    }
}
