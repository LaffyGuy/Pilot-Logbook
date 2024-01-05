package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentProfileBinding
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.SharedPreferencesAppSettings
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.ProfileViewModel
import com.example.pilotlogbook.utils.activityNavController
import com.example.pilotlogbook.utils.convertLongToTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var bindingClass: FragmentProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel>()
    lateinit var appSettings: AppSettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentProfileBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    bindingClass.tvTotalDailyFlightTime.text = convertLongToTime(it!!.totalDailyFlightTime)
                }
            }
        }
    }

    private fun logOut(){
        profileViewModel.logOut()
    }


}