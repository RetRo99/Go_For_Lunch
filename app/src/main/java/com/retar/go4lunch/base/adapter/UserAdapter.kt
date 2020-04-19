
package com.retar.go4lunch.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.recyclerview.widget.RecyclerView
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.utils.inflate
import com.retar.go4lunch.utils.loadRoundPhoto
import kotlinx.android.synthetic.main.item_fragment_users.view.*

class UserAdapter(private val isRestaurantDetails: Boolean = false) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    override fun getItemCount(): Int {
        return users.size
    }


    private var users: List<User> = listOf()

    fun update(modelList: List<User>) {
        users = modelList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindUser(users[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflatedView = parent.inflate(R.layout.item_fragment_users, false)
        return UserHolder(inflatedView)
    }


    inner class UserHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindUser(user: User) {

            view.run {
                val details = when {

                    isRestaurantDetails -> user.firstName.plus(" ").plus(context.getString(R.string.is_joining))

                    user.pickedRestaurantTitle.isNullOrEmpty() -> {
                        buildSpannedString {
                            italic { append(user.firstName.plus(" ").plus(context.getString(R.string.hasnt_decied_yet))) }
                        }
                    }

                    else -> user.firstName.plus(" ").plus(context.getString(R.string.is_eating_at)).plus(
                        " "
                    ).plus(user.pickedRestaurantTitle)

                }
                item_user_photo.loadRoundPhoto(user.photoUrl)
                item_user_details.text = details
            }
        }

    }
}
