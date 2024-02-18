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
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.databinding.FragmentProfileBinding
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.SharedPreferencesAppSettings
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.ProfileViewModel
import com.example.pilotlogbook.utils.activityNavController
import com.example.pilotlogbook.utils.convertLongToTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(), MenuProvider {
    lateinit var bindingClass: FragmentProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel>()
    lateinit var appSettings: AppSettings
//    lateinit var accountUpdateUsernameTuple: AccountUpdateUsernameTuple

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentProfileBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        appSettings = SharedPreferencesAppSettings(requireContext())

        bindingClass.btnLogOut.setOnClickListener {
            logOut()
            activityNavController().navigate(R.id.mainRegisterFragment, null, navOptions {
                popUpTo(R.id.navigationViewFragment){
                    inclusive = true
                }
            })
        }

        getAccountInfo()

    }

    private fun getAccountInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.findAccountById(appSettings.getCurrentAccountId()).collect{account ->
                account.let {
                    bindingClass.tvEmail.text = it?.email
                    bindingClass.tvFirstNameAndLastName.text = "${it?.firstName} ${it?.lastName}"
//                    if(it != null){
//                        accountUpdateUsernameTuple = AccountUpdateUsernameTuple(it.id, it.firstName, it.lastName)
//                    }
                    if(it!!.totalDailyFlightTime != null){
                        bindingClass.tvTotalDailyFlightTime.text = convertLongToTime(it.totalDailyFlightTime!!)
                    }else{
                        bindingClass.tvTotalDailyFlightTime.text = ""
                    }
                     Log.d("MyTag", "Total daily flight time - ${it.totalDailyFlightTime}")
                }
            }
        }
    }

    private fun updateFirstNameOrLastName() {



//        profileViewModel.updateFirstNameOrLastName()

//        viewLifecycleOwner.lifecycleScope.launch {
//            profileViewModel.findAccountById(appSettings.getCurrentAccountId()).collect{ account ->
//                account?.let {
//                    val accountUpdateUsernameTuple = AccountUpdateUsernameTuple(it.id, it.firstName, it.lastName)
//                    profileViewModel.updateFirstNameOrLastName(accountUpdateUsernameTuple)
//                }
//            }
//        }

    }

    private fun logOut(){
        profileViewModel.logOut()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.tool_bar_profile_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


}