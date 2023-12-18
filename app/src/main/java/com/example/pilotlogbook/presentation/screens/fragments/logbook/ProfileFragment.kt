package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentProfileBinding
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.ProfileViewModel
import com.example.pilotlogbook.utils.activityNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var bindingClass: FragmentProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentProfileBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.btnLogOut.setOnClickListener {
            logOut()
            activityNavController().navigate(R.id.mainRegisterFragment, null, navOptions {
                popUpTo(R.id.navigationViewFragment){
                    inclusive = true
                }
            })
        }

    }

    private fun logOut(){
        profileViewModel.logOut()
    }


}