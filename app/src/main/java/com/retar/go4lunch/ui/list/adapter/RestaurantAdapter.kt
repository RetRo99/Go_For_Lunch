package com.retar.go4lunch.ui.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.retar.go4lunch.R
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.utils.inflate
import com.retar.go4lunch.utils.loadPhotoFromUrl
import kotlinx.android.synthetic.main.fragment_list_item.view.*

class RestaurantAdapter(private val restaurants: List<RestaurantEntity>, val action: (String) -> Unit) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {


    inner class RestaurantHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bindRestaurant(restaurant: RestaurantEntity) {
            view.apply {
                fragment_list_restaurant_view_item_name.text = restaurant.name

                fragment_list_restaurant_view_item_address.text = restaurant.address()
                fragment_list_restaurant_view_item_distance.text = "${restaurant.distance()} m"
                fragment_list_restaurant_view_item_opening_hours.text = restaurant.isOpenedNow
                fragment_list_restaurant_view_item_image.loadPhotoFromUrl(restaurant.photoUrl)

                view.setOnClickListener{
                    action(restaurant.id)
                }

            }
        }


    }


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val inflatedView = parent.inflate(R.layout.fragment_list_item)
        return RestaurantHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bindRestaurant(restaurant)

    }
}