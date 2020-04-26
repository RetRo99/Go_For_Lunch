package com.retar.go4lunch.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.retar.go4lunch.R
import com.retar.go4lunch.base.BaseAutoCompleteFragment
import com.retar.go4lunch.base.adapter.UserAdapter
import com.retar.go4lunch.base.model.User
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class UsersFragment : BaseAutoCompleteFragment(), UsersView {

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

        adapter = UserAdapter()
        recyclerView.adapter = adapter

        autoSearch.doOnTextChanged { text, _, _, _ ->
            presenter.onSearchChanged(text)
        }

        presenter.onActivityCreated()
    }

    override fun showToast(stringResource: Int) {
        Toast.makeText(context, stringResource, Toast.LENGTH_SHORT).show()

    }

    companion object {

        const val TAG = "com.retar.go4lunch.ui.mates.matesfragment"

        @JvmStatic
        fun newInstance() = UsersFragment()
    }

    override fun setData(data: List<User>) {
        adapter.update(data)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
