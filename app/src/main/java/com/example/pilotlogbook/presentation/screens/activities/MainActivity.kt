package com.example.pilotlogbook.presentation.screens.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilotlogbook.R
import com.example.pilotlogbook.adapter.DailyFlightAdapter
import com.example.pilotlogbook.databinding.ActivityMainBinding
import com.example.pilotlogbook.domain.entities.DailyFlight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

//        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment.navController
//
//        NavigationUI.setupActionBarWithNavController(this, navController)

    }

}