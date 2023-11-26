package com.example.pilotlogbook.presentation.screens.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.ActivityLogBookBinding
import com.example.pilotlogbook.databinding.NavHeaderBinding
import com.example.pilotlogbook.presentation.viewmodels.LogBookViewModel
import com.example.pilotlogbook.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class LogBookActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityLogBookBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val splashViewModel by viewModels<SplashViewModel>()
    private val logBookViewModel by viewModels<LogBookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
              setKeepOnScreenCondition{
                    splashViewModel.isReady.value
              }
        }

        splashViewModel.isSignedIn.observe(this@LogBookActivity){
              Log.d("MyTag3", "IsSignIn- $it")
              choosePathToFragments(it, navController)
        }

        bindingClass = ActivityLogBookBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        setSupportActionBar(bindingClass.toolBar)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.start_flight, R.id.daily_flight), bindingClass.drawerLayout)
        bindingClass.toolBar.setupWithNavController(navController, appBarConfiguration)
        bindingClass.navigationView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(bindingClass.navigationView, navController)

        getAccountInfo()

    }


    private fun choosePathToFragments(isSignedIn: Boolean, navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraph())
        graph.setStartDestination(
            if(isSignedIn){
                getDailyFlightDestination()
            }else{
                getSignInDestination()
            }
        )

        navController.graph = graph
    }


    private fun getMainNavigationGraph() = R.navigation.log_book_nav_graph

    private fun getDailyFlightDestination() = R.id.start_flight

    private fun getSignInDestination() = R.id.signInFragment

    private fun getAccountInfo() {
        lifecycleScope.launch {
            logBookViewModel.getUserNameById().collect{ account ->
                val navigationView = bindingClass.navigationView
                val headerBinding = NavHeaderBinding.bind(navigationView.getHeaderView(0))
                headerBinding.userNameAndLastName.text = account?.firstName + account?.lastName

            }
        }
    }


}