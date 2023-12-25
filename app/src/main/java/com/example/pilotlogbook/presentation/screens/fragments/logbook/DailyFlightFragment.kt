package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
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
import com.example.pilotlogbook.adapter.DailyFlightPagerAdapter
import com.example.pilotlogbook.databinding.FragmentDailyFlightBinding
import com.example.pilotlogbook.databinding.PartResultBinding
import com.example.pilotlogbook.domain.ErrorResult
import com.example.pilotlogbook.domain.LoadingResult
import com.example.pilotlogbook.domain.SuccessResult
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.DailyFlightViewModel
import com.example.pilotlogbook.utils.activityNavController

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyFlightFragment : Fragment(), MenuProvider {
    lateinit var bindingClass: FragmentDailyFlightBinding
    private val dailyFlightViewModel by viewModels<DailyFlightViewModel>()
    lateinit var dailyFlightAdapter: DailyFlightAdapter
    lateinit var pagerAdapter: DailyFlightPagerAdapter
//    lateinit var partResultBinding: PartResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentDailyFlightBinding.inflate(layoutInflater)
//        partResultBinding = PartResultBinding.bind(bindingClass.root)



        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

//        initDailyFlightAdapter()

        initPagerAdapter()

        observePagerDailyFlight()

//        test()

        dailyFlightViewModel.searchBy.observe(viewLifecycleOwner){
            Log.d("MyTag2", "Observe value - $it")
        }

        bindingClass.etSearch.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                dailyFlightViewModel.setSearchBy(p0 ?: "")
                Log.d("MyTag2", "Value - $p0")
                return true

            }

        })


    }


//    private fun initDailyFlightAdapter(){
//        dailyFlightAdapter = DailyFlightAdapter()
//        bindingClass.rvDailyFlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        bindingClass.rvDailyFlight.adapter = dailyFlightAdapter
//    }


    private fun navigateToAddDailyFlightFragment(){
        activityNavController().navigate(R.id.addDailyFlightFragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.addDailyFlightFragmentToolBar -> navigateToAddDailyFlightFragment()
        }
        return true
    }

//    private fun test(){
//        dailyFlightViewModel.searchQuery.observe(viewLifecycleOwner){
//            dailyFlightAdapter.differ.submitList(it)
//        }
//    }

//    private fun getAll(searchBy: String) {
//        dailyFlightViewModel.getAllDailyFlightTest(searchBy).observe(viewLifecycleOwner){
//            dailyFlightAdapter.differ.submitList(it)
//        }
//    }

    private fun initPagerAdapter(){
        pagerAdapter = DailyFlightPagerAdapter()
        bindingClass.rvDailyFlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rvDailyFlight.adapter = pagerAdapter
    }

    private fun observePagerDailyFlight(){
        Log.d("MyTag2", "Observe")
        viewLifecycleOwner.lifecycleScope.launch{
            dailyFlightViewModel.dailyFlightFlow.collectLatest { pagingData ->
                Log.d("MyTag3", "Data - $pagingData")
                pagerAdapter.submitData(pagingData)
            }
        }
    }


}



