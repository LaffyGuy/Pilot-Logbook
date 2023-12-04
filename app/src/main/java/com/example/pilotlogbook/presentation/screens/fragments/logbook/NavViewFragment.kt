package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentNavViewBinding


class NavViewFragment : Fragment() {
    lateinit var bindingClass: FragmentNavViewBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val topLevelDestination = setOf(R.id.dailyFlightFragment, R.id.startFlightFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentNavViewBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        prepareToolbar()

    }

    private fun prepareToolbar(){

        (activity as AppCompatActivity).setSupportActionBar(bindingClass.toolBar)

        val appBarConfiguration = AppBarConfiguration(topLevelDestination, bindingClass.drawerLayOut)

        bindingClass.toolBar.setupWithNavController(navController, appBarConfiguration)

        bindingClass.navigationView.setupWithNavController(navController)
    }


}