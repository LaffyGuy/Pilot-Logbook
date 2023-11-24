package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pilotlogbook.databinding.FragmentSplashBinding
import com.example.pilotlogbook.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    lateinit var bindingClass: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentSplashBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launch {
//            delay(2000)
//            val intent = Intent(requireContext(), LogBookActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
//            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
//        }

    }

}