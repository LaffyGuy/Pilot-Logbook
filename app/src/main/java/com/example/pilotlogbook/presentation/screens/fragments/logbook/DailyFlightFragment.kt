package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuProvider
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilotlogbook.R
import com.example.pilotlogbook.adapter.DailyFlightAction
import com.example.pilotlogbook.adapter.DailyFlightPagerAdapter
import com.example.pilotlogbook.adapter.DefaultLoadStateAdapter
import com.example.pilotlogbook.databinding.FragmentDailyFlightBinding
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.DailyFlightViewModel
import com.example.pilotlogbook.utils.SortType
import com.example.pilotlogbook.utils.activityNavController
import com.example.pilotlogbook.utils.simpleScan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyFlightFragment : Fragment(), MenuProvider {
    lateinit var bindingClass: FragmentDailyFlightBinding
    private val dailyFlightViewModel by viewModels<DailyFlightViewModel>()
    lateinit var pagerAdapter: DailyFlightPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentDailyFlightBinding.inflate(layoutInflater)

        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        initPagerAdapter()

        observePagerDailyFlight()

        setUpSwipeToRefresh()

        bindingClass.etSearch.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                dailyFlightViewModel.setSearchBy(p0 ?: "")
                return true

            }

        })

        dailyFlightViewModel.setSortType(SortType.DEFAULT)

    }

    private fun setUpSwipeToRefresh(){
        bindingClass.swipeToRefreshLayout.setOnRefreshListener {
          dailyFlightViewModel.refresh()
            bindingClass.swipeToRefreshLayout.isRefreshing = false
        }
    }

    private fun navigateToAddDailyFlightFragment(){
        activityNavController().navigate(R.id.addDailyFlightFragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.addDailyFlightFragmentToolBar -> navigateToAddDailyFlightFragment()
            R.id.standard -> dailyFlightViewModel.setSortType(SortType.DEFAULT)
            R.id.newItem -> dailyFlightViewModel.setSortType(SortType.DATE_DESC)
            R.id.old -> dailyFlightViewModel.setSortType(SortType.DATE_ASC)
            R.id.timeFlightDesc -> dailyFlightViewModel.setSortType(SortType.TTF_DESC)
            R.id.timeFlightAsc -> dailyFlightViewModel.setSortType(SortType.TTF_ASC)
        }
        return true
    }


    private fun initPagerAdapter(){
        pagerAdapter = DailyFlightPagerAdapter(object : DailyFlightAction {
            override fun dailyFlightDetails(dailyFlight: DailyFlight) {
                val direction = DailyFlightFragmentDirections.actionDailyFlightFragmentToDetailsDailyFlightFragment(dailyFlight)
                findNavController().navigate(direction)
            }

        })

        val footerAdapter = DefaultLoadStateAdapter()

        val adapterWithLoadState = pagerAdapter.withLoadStateFooter(footerAdapter)

        bindingClass.rvDailyFlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rvDailyFlight.adapter = adapterWithLoadState

        handleScrollingToTopWhenSearching(pagerAdapter)
        handleListVisibility(pagerAdapter)
    }

    private fun handleScrollingToTopWhenSearching(adapter: DailyFlightPagerAdapter) = viewLifecycleOwner.lifecycleScope.launch {
       getRefreshLoadStateFlow(adapter)
           .simpleScan(count = 2)
           .collectLatest { (previousState, currentState) ->
                if(previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                      bindingClass.rvDailyFlight.scrollToPosition(0)
           }
       }
    }

    private fun handleListVisibility(adapter: DailyFlightPagerAdapter) = viewLifecycleOwner.lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 3)
            .collectLatest {(beforePrevious, previous, current) ->
                bindingClass.rvDailyFlight.isInvisible = current is LoadState.Error
                    || previous is LoadState.Error
                    || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
        }
    }


    private fun getRefreshLoadStateFlow(adapter: DailyFlightPagerAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map {
            it.refresh
        }
    }

    private fun observePagerDailyFlight(){
        viewLifecycleOwner.lifecycleScope.launch{
            dailyFlightViewModel.dailyFlightFlow.collectLatest { pagingData ->
                pagerAdapter.submitData(pagingData)
            }
        }
    }


}



