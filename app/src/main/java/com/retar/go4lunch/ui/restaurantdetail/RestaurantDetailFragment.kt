package com.retar.go4lunch.ui.restaurantdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.restaurantdetail.adapter.PhotosAdapter
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*
import javax.inject.Inject

class RestaurantDetailFragment : DaggerFragment(), RestaurantDetailView {
    private val args: RestaurantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var presenter: RestaurantDetailPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.onActivityCreated(args.id)
    }

    override fun showData(data: UiRestaurantDetailItem) {
        locationTextView.text = data.address
        titleTextView.text = data.name
        callConstraint.setOnClickListener {
            if(!data.phoneNumber.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${data.phoneNumber}")
                startActivity(intent)
            }else{
                //todo extract text
                Toast.makeText(context, "Not avaliable", Toast.LENGTH_LONG).show()
            }

        }

        websiteConstraint.setOnClickListener {

            if (!data.webPage.isNullOrEmpty()){
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(data.webPage)
                startActivity(openURL)
            }else{
                //todo extract text
                Toast.makeText(context, "Not avaliable", Toast.LENGTH_LONG).show()
            }

        }

        imageViewPager.adapter = PhotosAdapter(context, data.photoReferences)
        indicator.setupWithViewPager(imageViewPager, true)


    }

    override fun onDestroy() {
        Log.d("čič", "onDestroy")
        presenter.onDestroy()
        super.onDestroy()
    }


}