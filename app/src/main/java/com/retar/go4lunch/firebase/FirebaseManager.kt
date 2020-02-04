package com.retar.go4lunch.firebase

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    private val auth: FirebaseAuth

) {


    fun getCurrentUser(): Maybe<FirebaseUser> {
        val user = auth.currentUser
       return  if (user != null) Maybe.just(user) else Maybe.empty<FirebaseUser>()
    }
}
