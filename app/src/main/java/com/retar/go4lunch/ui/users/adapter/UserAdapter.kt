package com.retar.go4lunch.ui.users.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.users.model.User
import com.retar.go4lunch.utils.inflate
import com.retar.go4lunch.utils.loadRoundPhoto
import kotlinx.android.synthetic.main.item_fragment_users.view.*

class UserAdapter(private var users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {
    override fun getItemCount(): Int {
        return users.size
    }

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


    class UserHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindUser(user: User) {
            view.run {
                item_user_photo.loadRoundPhoto(user.photoUrl)
                item_user_details.text = user.name
            }
        }

    }
}