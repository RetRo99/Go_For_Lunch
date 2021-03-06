package com.retar.go4lunch.ui.resturants.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
                item_resturant_people_visiting.visibility = View.GONE
                val openedText = getOpenedText(restaurant.openedText, context)

                item_restaurant_name.text = restaurant.name
                item_restaurant_address.text = restaurant.address()
                item_restaurant_distance.text = restaurant.distance
                item_restaurant_hours.text = openedText
                item_restaurant_photo.loadRestaurantPhoto(restaurant.photoUrl)

                item_resturant_people_visiting.run {
                    if (restaurant.timesPicked != 0) {
                        text = restaurant.peopleVisiting()
                        visibility = View.VISIBLE
                    } else {

                        visibility = View.GONE
                    }
                }

                item_restaurant_ratingBar.run{
                    if (restaurant.rating != null) {
                        visibility = View.VISIBLE

                        rating = restaurant.rating as Float
                    }else {
                        visibility = View.GONE
                    }
                }


                view.setOnClickListener {
                    action(restaurant)
                }

            }
        }

    }

    private fun getOpenedText(
        openedText: Pair<Int, String>,
        context: Context
    ): CharSequence {

        return when (openedText.first) {
            TYPE_NO_INFORMATION -> context.getString(R.string.restaurant_no_information)
            TYPE_OPEN_UNTIL -> context.getString(
                R.string.restaurant_opened_until,
                openedText.second
            )
            TYPE_OPEN_24 -> context.getString(R.string.open_247)
            TYPE_CLOSING_SOON -> {
                SpannableString(context.getString(R.string.restaurant_closing_soon)).apply {
                    setSpan(
                        ForegroundColorSpan(Color.RED),
                        0,
                        this.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        this.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                }


            }

            TYPE_CLOSED -> context.getString(R.string.restaurant_closed)
            else -> throw Exception("unknown type")
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

    companion object {
        const val TYPE_NO_INFORMATION = 1
        const val TYPE_CLOSING_SOON = 2
        const val TYPE_OPEN_UNTIL = 3
        const val TYPE_CLOSED = 4
        const val TYPE_OPEN_24 = 5


    }
}
