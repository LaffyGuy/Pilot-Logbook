package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentSignInBinding
import com.example.pilotlogbook.presentation.viewmodels.registerviewmodel.SignInViewModel
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.activityNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
   lateinit var bindingClass: FragmentSignInBinding
   private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentSignInBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindingClass.btnSingUp.setOnClickListener {
            navigateToSignUpFragment()
        }

        bindingClass.btnLogin.setOnClickListener {
          signIn()
        }

        observeId()

        observeState()

    }

    private fun signIn(){
        signInViewModel.signIn(bindingClass.etEmail.text.toString(), bindingClass.etPassword.text.toString())
    }

    private fun observeId() {
        signInViewModel.id.observe(viewLifecycleOwner) {
            if (it != null) {
                activityNavController().navigate(R.id.navigationViewFragment, null, navOptions {
                    popUpTo(R.id.mainRegisterFragment) {
                        inclusive = true
                    }
                })
            }
        }
    }


    private fun observeState(){
        signInViewModel.state.observe(viewLifecycleOwner){
            fillError(bindingClass.etEmail, it.emailErrorMessage)
            fillError(bindingClass.etPassword, it.passwordErrorMessage)

            bindingClass.progressBar.visibility = if(it.showProgress) View.VISIBLE else View.INVISIBLE

        }

    }

    private fun fillError(input: EditText, stringRes: Int){
        if(stringRes == NO_ERROR_MESSAGE){
            input.error = null
        }else{
            input.error = getString(stringRes)
        }
    }

    private fun navigateToSignUpFragment(){
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null, navOptions {
            popUpTo(R.id.mainRegisterFragment){
                inclusive = true
            }
        })
    }

}
