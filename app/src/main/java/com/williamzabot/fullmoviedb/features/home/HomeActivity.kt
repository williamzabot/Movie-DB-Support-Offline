package com.williamzabot.fullmoviedb.features.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.features.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.home_toolbar))
        checkLogin()
        configNavigation()
    }

    private fun checkLogin() {
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun configNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoriteFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ic_exit -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
