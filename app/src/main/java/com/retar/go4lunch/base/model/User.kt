package com.retar.go4lunch.base.model

data class User(
    val id: String = "",
    val name: String? = null,
    val photoUrl: String? = null,
    val email: String? = null,
    val phone: String? = "null",
    val pickedRestaurant: String = "",
    val pickedRestaurantTitle: String? = ""
){
    val firstName:String?
        get() {
          return name?.substringBefore(" ")
        }
}
