package com.retar.go4lunch.ui.resturants.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.utils.inflate
import com.retar.go4lunch.utils.loadRestaurantPhoto
import kotlinx.android.synthetic.main.item_fragment_list.view.*

class RestaurantAdapter(private val action: (RestaurantEntity) -> Unit) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {


    private var restaurants: List<RestaurantEntity> = listOf()

    fun setData(data:List<RestaurantEntity>){
        restaurants = data
        this.notifyDataSetChanged()
    }

    inner class RestaurantHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindRestaurant(restaurant: RestaurantEntity) {
            view.apply {
                item_restaurant_name.text = restaurant.name

                item_restaurant_address.text = restaurant.address()
                item_restaurant_distance.text = "${restaurant.distance()} m"
                item_restaurant_hours.text = restaurant.isOpenedNow
                item_restaurant_photo.loadRestaurantPhoto(restaurant.photoUrl)

                view.setOnClickListener{
                    action(restaurant)
                }

            }
        }


    }


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val inflatedView = parent.inflate(R.layout.item_fragment_list)
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