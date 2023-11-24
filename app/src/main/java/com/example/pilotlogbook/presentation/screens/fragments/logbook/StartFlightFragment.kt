package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentStartFlightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFlightFragment : Fragment() {
    lateinit var bindingClass: FragmentStartFlightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentStartFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}