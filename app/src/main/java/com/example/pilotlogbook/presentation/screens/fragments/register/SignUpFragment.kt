package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentSignUpBinding
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.presentation.viewmodels.registerviewmodel.SignUpViewModel
import com.example.pilotlogbook.data.validation.SignUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var bindingClass: FragmentSignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentSignUpBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.btnSignUp.setOnClickListener {
           signUpViewModel.state.observe(viewLifecycleOwner){
               if(it.success){
                   findNavController().navigate(R.id.action_signUpFragment_to_start_flight)
               }
           }
            createAccount()
        }

        observeRegistrationValidation()

    }

    private fun createAccount(){
        val signUp = SignUp(
            bindingClass.etName.text.toString(),
            bindingClass.etLastName.text.toString(),
            bindingClass.etEmail.text.toString(),
            bindingClass.etPassword.text.toString(),
            bindingClass.etRepeatPassword.text.toString())
        signUpViewModel.createAccount(signUp)

    }

    private fun observeRegistrationValidation(){
        signUpViewModel.validation.observe(viewLifecycleOwner){
            if(it.name is ValidationResult.Failed){
                bindingClass.etName.apply {
                    requestFocus()
                    error = it.name.error
                }
            }
            if(it.lastName is ValidationResult.Failed){
                bindingClass.etLastName.apply {
                    requestFocus()
                    error = it.lastName.error
                }
            }
            if(it.email is ValidationResult.Failed){
                bindingClass.etEmail.apply {
                    requestFocus()
                    error = it.email.error
                }
            }
            if(it.password is ValidationResult.Failed){
                bindingClass.etPassword.apply {
                    requestFocus()
                    error = it.password.error
                }
            }
            if(it.repeatPassword is ValidationResult.Failed){
                bindingClass.etRepeatPassword.apply {
                    requestFocus()
                    error = it.repeatPassword.error
                }
            }
        }
    }

}