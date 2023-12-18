package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import com.example.pilotlogbook.databinding.FragmentDailyFlightBinding
import com.example.pilotlogbook.databinding.PartResultBinding
import com.example.pilotlogbook.domain.ErrorResult
import com.example.pilotlogbook.domain.LoadingResult
import com.example.pilotlogbook.domain.SuccessResult
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.DailyFlightViewModel
import com.example.pilotlogbook.utils.activityNavController

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyFlightFragment : Fragment(), MenuProvider {
    lateinit var bindingClass: FragmentDailyFlightBinding
    private val dailyFlightViewModel by viewModels<DailyFlightViewModel>()
    lateinit var dailyFlightAdapter: DailyFlightAdapter
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

        initDailyFlightAdapter()

//        getAllDailyFlightLog()

//        observeResult()

    }


    private fun initDailyFlightAdapter(){
        dailyFlightAdapter = DailyFlightAdapter()
        bindingClass.rvDailyFlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rvDailyFlight.adapter = dailyFlightAdapter
    }


//    private fun getAllDailyFlightLog(){
//        val partResultBinding = PartResultBinding.bind(bindingClass.root)
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                delay(2000)
//                dailyFlightViewModel.getAllDailyFlightLog().collect{ result ->
//                    when(result){
//                        is LoadingResult -> {
//                            bindingClass.horizontalScrollView.visibility = View.GONE
//                            partResultBinding.mainLinearLayout.visibility = View.GONE
//                            partResultBinding.progressBar.visibility = View.VISIBLE
//                        }
//                        is SuccessResult -> {
//                            bindingClass.horizontalScrollView.visibility = View.VISIBLE
//                            partResultBinding.mainLinearLayout.visibility = View.GONE
//                            partResultBinding.progressBar.visibility = View.GONE
//
//                            dailyFlightAdapter.differ.submitList(result.data)
//                        }
//                        is ErrorResult -> {
//                            bindingClass.horizontalScrollView.visibility = View.GONE
//                            partResultBinding.mainLinearLayout.visibility = View.VISIBLE
//                            partResultBinding.progressBar.visibility = View.GONE
//
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private fun observeResult() {
//        dailyFlightViewModel.getDailyFlight().observe(viewLifecycleOwner){
//
//            dailyFlightAdapter.differ.submitList(it)
//            bindingClass.tvTotalFlightTime.text = it.sumOf { it.totalTimeOfFlight ?: 0 }.toString()
//    }


//    private fun observeResult(){
//        dailyFlightViewModel.pending.observe(viewLifecycleOwner){ result ->
//            pendingResult(bindingClass.root, result,
//            onLoading = {
//                Log.d("MyTag13", "Loading Result")
//                bindingClass.mainLinearLayout1.visibility = View.GONE
//                bindingClass.progressBar.visibility = View.VISIBLE
//                bindingClass.horizontalScrollView.visibility = View.GONE
//            },
//            onSuccess = {
//                Log.d("MyTag13", "Success Result")
//                bindingClass.mainLinearLayout1.visibility = View.GONE
//                bindingClass.progressBar.visibility = View.GONE
//                bindingClass.horizontalScrollView.visibility = View.VISIBLE
//
//                dailyFlightAdapter.differ.submitList(it)
//            },
//            onError = {
//                Log.d("MyTag13", "Error Result")
//                bindingClass.mainLinearLayout1.visibility = View.VISIBLE
//                bindingClass.progressBar.visibility = View.GONE
//                bindingClass.horizontalScrollView.visibility = View.GONE
//            })
//            when(result){
//                is LoadingResult -> {
//                    Log.d("MyTag13", "Loading Result")
//                    partResultBinding.mainLinearLayout.visibility = View.GONE
//                    partResultBinding.progressBar.visibility = View.VISIBLE
//                    bindingClass.horizontalScrollView.visibility = View.GONE
//                }
//                is SuccessResult -> {
//                    Log.d("MyTag13", "Success Result")
//                    partResultBinding.mainLinearLayout.visibility = View.GONE
//                    partResultBinding.progressBar.visibility = View.GONE
//                    bindingClass.horizontalScrollView.visibility = View.VISIBLE
//
//                    dailyFlightAdapter.differ.submitList(result.data)
//                }
//                is ErrorResult -> {
//                    Log.d("MyTag13", "Error Result")
//                    partResultBinding.mainLinearLayout.visibility = View.VISIBLE
//                    partResultBinding.progressBar.visibility = View.GONE
//                    bindingClass.horizontalScrollView.visibility = View.GONE
//                }
//            }
//        }

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
}



