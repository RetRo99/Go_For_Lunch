package com.retar.go4lunch.firebase.model

data class User(
    val id: String,
    val name: String?,
    val photoUrl: String?,
    val email: String?,
    val phone: String?
)