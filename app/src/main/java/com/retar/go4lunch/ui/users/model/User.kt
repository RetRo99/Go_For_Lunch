package com.retar.go4lunch.ui.users.model

data class User(
    val id: String = "",
    val name: String? = null,
    val photoUrl: String? = null,
    val email: String? = null,
    val phone: String? = null
)