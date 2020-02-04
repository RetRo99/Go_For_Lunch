package com.retar.go4lunch.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.retar.go4lunch.R
import com.retar.go4lunch.base.LocationPermissionActivity
import com.retar.go4lunch.ui.list.ListFragmentDirections
import com.retar.go4lunch.ui.map.MapFragmentDirections
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
        presenter.onCreate()

    }

    override fun startApp(user: FirebaseUser) {

        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration.Builder(setOf(
            R.id.navigation_map,
            R.id.navigation_list,
            R.id.navigation_mates
        )).setDrawerLayout(drawer_layout).build()

        toolbar.setupWithNavController(getNavController(), appBarConfiguration)

        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)
        toolbar.menu.clear()
        header.apply {

            nav_img_profile.loadRoundPhoto(user.photoUrl.toString())
            nav_email.text = user.email
            nav_name.text = user.displayName
        }
        NavigationUI.setupWithNavController(bottomNavigationView, getNavController())

    }

    override fun loginUser() {
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
            ListFragmentDirections.actionListToDetail(
                title,
                id
            )
        )
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        private const val RC_SIGN_IN = 12
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                //todo autocomplete
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                presenter.onUserLogin()
            } else {
                loginUser()
            }
        }
    }}

