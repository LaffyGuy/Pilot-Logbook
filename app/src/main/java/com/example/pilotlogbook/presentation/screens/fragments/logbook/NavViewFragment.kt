package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentNavViewBinding
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.SharedPreferencesAppSettings
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavViewFragment : Fragment() {
    lateinit var bindingClass: FragmentNavViewBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val topLevelDestination = setOf(R.id.dailyFlightFragment, R.id.startFlightFragment, R.id.profileFragment)
    private val profileViewModel by viewModels<ProfileViewModel>()
    lateinit var appSettings: AppSettings

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

        appSettings = SharedPreferencesAppSettings(requireContext())

        prepareNavHeader()

    }

    private fun prepareToolbar(){

        (activity as AppCompatActivity).setSupportActionBar(bindingClass.toolBar)

        val appBarConfiguration = AppBarConfiguration(topLevelDestination, bindingClass.drawerLayOut)

        bindingClass.toolBar.setupWithNavController(navController, appBarConfiguration)

        bindingClass.navigationView.setupWithNavController(navController)

    }

    @SuppressLint("ResourceType")
    private fun prepareNavHeader(){
        val headerView = bindingClass.navigationView.getHeaderView(0)
        val headerText = headerView.findViewById<TextView>(R.id.userNameAndLastName)
        val headerImage = headerView.findViewById<ImageView>(R.id.accountIcon)

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.findAccountById(appSettings.getCurrentAccountId()).collect{ account ->
                account?.let {
                    headerText.text = "${it.firstName} ${it.lastName}"
                    Glide.with(this@NavViewFragment).load(account.imagePath).placeholder(R.drawable.ic_add_flight_person).into(headerImage)
                }
            }
        }



    }


}