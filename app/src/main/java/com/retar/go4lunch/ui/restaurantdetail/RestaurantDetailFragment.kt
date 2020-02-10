package com.retar.go4lunch.ui.restaurantdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.restaurantdetail.adapter.PhotosAdapter
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import com.retar.go4lunch.ui.users.adapter.UserAdapter
import com.retar.go4lunch.base.model.User
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*
import javax.inject.Inject

class RestaurantDetailFragment : DaggerFragment(), RestaurantDetailView {

    private val args: RestaurantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var presenter: RestaurantDetailPresenter

    private lateinit var adapter:UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = UserAdapter(listOf())
        details_recycler.adapter = adapter

        presenter.onActivityCreated(args.id)
    }

    override fun showData(data: UiRestaurantDetailItem) {
        locationTextView.text = data.address
        titleTextView.text = data.name
        callConstraint.setOnClickListener {
            if (!data.phoneNumber.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${data.phoneNumber}")
                startActivity(intent)
            } else {
                //todo extract text
                Toast.makeText(context, "Not available", Toast.LENGTH_LONG).show()
            }

        }

        websiteConstraint.setOnClickListener {

            if (!data.webPage.isNullOrEmpty()) {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(data.webPage)
                startActivity(openURL)
            } else {
                //todo extract text
                Toast.makeText(context, "Not available", Toast.LENGTH_LONG).show()
            }

        }

        fab_pick_restaurant.setOnClickListener {
            fab_pick_restaurant.isEnabled = false
            presenter.onFabClick()
        }

        imageViewPager.adapter = PhotosAdapter(context, data.photoReferences)
        indicator.setupWithViewPager(imageViewPager, true)

        details_progress.visibility = View.GONE
        details_container.visibility = View.VISIBLE


    }

    override fun setFab(picked: Boolean) {
        if (picked) {
            fab_pick_restaurant.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_checked
                )
            )
        } else {
            fab_pick_restaurant.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_not_check
                )
            )
        }

        fab_pick_restaurant.isEnabled = true
    }

    override fun showUsers(users: List<User>) {
        adapter.update(users)
    }


    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}