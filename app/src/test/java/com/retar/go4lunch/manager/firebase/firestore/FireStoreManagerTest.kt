package com.retar.go4lunch.manager.firebase.firestore

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl.Companion.CURRENT_PICKED
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class FireStoreManagerTest {

    private lateinit var firestoneManager: FireStoreManagerImpl
    @Before
    fun setUp() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
        firestoneManager = FireStoreManagerImpl(FirebaseFirestore.getInstance())
    }

    @Test
    fun getCurrentUser() {
        firestoneManager.currentUser =
            User("123", "name", "photoUrl", "email", "phoneNumber", "pickedId", "pickedTitle")

        val currentUser = firestoneManager.currentUser
        assertThat(currentUser?.id, `is`("123"))
        assertThat(currentUser?.name, `is`("name"))
        assertThat(currentUser?.photoUrl, `is`("photoUrl"))
        assertThat(currentUser?.email, `is`("email"))
        assertThat(currentUser?.phone, `is`("phoneNumber"))
        assertThat(currentUser?.pickedRestaurant, `is`("pickedId"))
        assertThat(currentUser?.pickedRestaurantTitle, `is`("pickedTitle"))


    }


    @Test
    fun onRestaurantPickedAddVisit() {
        val mockedManager = Mockito.spy(firestoneManager)
        val id = "test"
        Mockito.doReturn(true).`when`(mockedManager).checkIfPicked(id)
        mockedManager.onRestaurantPicked(id, "more test")
            .test()
            .assertValue(CURRENT_PICKED)
            .dispose()
    }


    @Test
    fun checkIfPicked() {
        firestoneManager.currentUser =
            User("123", "name", "photoUrl", "email", "phoneNumber", "pickedId", "pickedTitle")
        assertThat(firestoneManager.checkIfPicked("pickedId"), `is`(true))
        assertThat(firestoneManager.checkIfPicked("test"), `is`(false))
    }
}
