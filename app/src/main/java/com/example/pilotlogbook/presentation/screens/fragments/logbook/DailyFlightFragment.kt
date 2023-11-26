package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilotlogbook.R
import com.example.pilotlogbook.adapter.DailyFlightAdapter
import com.example.pilotlogbook.databinding.FragmentDailyFlightBinding
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.DailyFlightViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyFlightFragment : Fragment() {
    lateinit var bindingClass: FragmentDailyFlightBinding
    private val dailyFlightViewModel by viewModels<DailyFlightViewModel>()
    lateinit var dailyFlightAdapter: DailyFlightAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentDailyFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tool_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.addDailyFlightFragment -> {
                        navigateToAddDailyFlightFragment()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner)

        initDailyFlightAdapter()

        getAllDailyFlightLog()

    }


    private fun initDailyFlightAdapter(){
        dailyFlightAdapter = DailyFlightAdapter()
        bindingClass.rvDailyFlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rvDailyFlight.adapter = dailyFlightAdapter
    }

    private fun getAllDailyFlightLog(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dailyFlightViewModel.getAllDailyFlightLog().collect{
                    dailyFlightAdapter.differ.submitList(it)
                }
            }
        }
    }

    private fun navigateToAddDailyFlightFragment(){
        findNavController().navigate(R.id.action_daily_flight_to_addDailyFlightFragment)
    }

}