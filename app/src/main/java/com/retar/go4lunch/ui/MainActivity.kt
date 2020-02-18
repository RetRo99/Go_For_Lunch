package com.retar.go4lunch.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.navigation.NavigationView
import com.retar.go4lunch.R
import com.retar.go4lunch.base.LocationPermissionActivity
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.ui.map.MapFragmentDirections
import com.retar.go4lunch.ui.resturants.RestaurantsFragmentDirections
import com.retar.go4lunch.utils.loadRoundPhoto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import javax.inject.Inject

class MainActivity : LocationPermissionActivity(), MainView,
    NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: MainViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.navigation_map,
                R.id.navigation_list,
                R.id.navigation_mates
            )
        ).setDrawerLayout(drawer_layout).build()

        toolbar.setupWithNavController(getNavController(), appBarConfiguration)

        navigationView.setNavigationItemSelectedListener(this)

        NavigationUI.setupWithNavController(bottomNavigationView, getNavController())
    }

    override fun onResume() {
        presenter.onResume()
        super.onResume()
    }

    override fun setDrawerData(user: User) {

        val header = navigationView.getHeaderView(0)

        header.apply {
            nav_img_profile.loadRoundPhoto(user.photoUrl)
            nav_email.text = user.email
            nav_name.text = user.name
        }

    }

    override fun requestLogin() {
        val customLayout = AuthMethodPickerLayout.Builder(R.layout.activity_login)
            .setGoogleButtonId(R.id.signIn_google)
            .setFacebookButtonId(R.id.signIn_facebook)
            .setTwitterButtonId(R.id.signIn_twitter)
            .build()

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers as MutableList<AuthUI.IdpConfig>)
                .setAuthMethodPickerLayout(customLayout)
                .setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun fromMapToResturantDetail(id: String, title: String) {
        getNavController().navigate(
            MapFragmentDirections.actionMapToDetail(
                title,
                id
            )
        )
    }

    override fun fromListToResturantDetail(id: String, title: String) {
        getNavController().navigate(
            RestaurantsFragmentDirections.actionListToDetail(
                title,
                id
            )
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                presenter.onLogoutClicked()
                return true
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showLogOutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_logout_title))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                presenter.onLogoutConfirmed()
            }
            .setNegativeButton(getString(R.string.dialog_no)) { _, _ ->
            }
            .create()
            .show()

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        private const val RC_SIGN_IN = 12
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val response = IdpResponse.fromResultIntent(data)
                presenter.onSignIn(response?.isNewUser)
            }
        }
    }
}



