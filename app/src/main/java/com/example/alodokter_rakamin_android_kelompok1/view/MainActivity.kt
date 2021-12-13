package com.example.alodokter_rakamin_android_kelompok1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    companion object{
        const val TO_LOGIN = "go_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        var type = intent.getStringExtra(TO_LOGIN)
        navView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_home -> {
                    when(type){
                        "login" -> {
                            navView.visibility = View.GONE
                            type = "not_login"
                            navController.navigate(R.id.loginFragment)
                        }
                        else -> {
                            navView.visibility = View.VISIBLE
                        }
                    }
                }
                R.id.navigation_consult -> {
                    if(SharedPreferences(this).getLoggedStatus()){
                        navView.visibility = View.VISIBLE
                    }
                    else {
                        navView.visibility = View.GONE
                        navController.navigate(R.id.loginFragment)
                    }
                }
                R.id.navigation_history -> {
                    if(SharedPreferences(this).getLoggedStatus()){
                        navView.visibility = View.VISIBLE
                    }
                    else {
                        navView.visibility = View.GONE
                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
        }
        navView.setupWithNavController(navController)
        if(SharedPreferences(this).isFirstTimeLaunch()) SharedPreferences(this).setFirstTimeLaunch(false)
    }

    override fun onBackPressed() {
        when(navController.currentDestination?.id){
            R.id.loginFragment -> navView.selectedItemId = R.id.navigation_home
            R.id.registerFragment -> navController.popBackStack()
            else -> super.onBackPressed()
        }
    }
}