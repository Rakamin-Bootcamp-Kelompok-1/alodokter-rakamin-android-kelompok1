package com.example.alodokter_rakamin_android_kelompok1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_home -> {
                    if(SharedPreferences(this).isFirstTimeLaunch()) {
                        navView.visibility = View.GONE
                        SharedPreferences(this).setFirstTimeLaunch(false)
                        navController.navigate(R.id.loginFragment)
                    }
                    else {
                        navView.visibility = View.VISIBLE
                    }
                }
                R.id.loginFragment -> {
                    if(SharedPreferences(this).getLoggedStatus()){
                        navView.visibility = View.VISIBLE
                    }
                }
            }
        }
        navView.setupWithNavController(navController)
    }
}