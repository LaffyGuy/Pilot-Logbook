package com.example.pilotlogbook.presentation.screens.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.ActivityLogBookBinding
import com.example.pilotlogbook.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogBookActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityLogBookBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val splashViewModel by viewModels<SplashViewModel>()

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

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

//        NavigationUI.setupWithNavController(bindingClass.navigationView, navController)
//        NavigationUI.setupActionBarWithNavController(this, navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, bindingClass.drawerLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
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


}