package com.retar.go4lunch.ui.restaurantdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.retar.go4lunch.R
import com.retar.go4lunch.utils.loadRestaurantPhoto
import kotlinx.android.synthetic.main.view_restaurant_detail_photo.view.*

class PhotosAdapter(context: Context?, private val photoReferences: List<String>) :
    PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return photoReferences.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.view_restaurant_detail_photo, view, false)!!



        imageLayout.restaurantDetailPhoto.loadRestaurantPhoto(photoReferences[position])

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


}
