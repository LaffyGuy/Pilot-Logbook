package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentSignUpBinding
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.presentation.viewmodels.registerviewmodel.SignUpViewModel
import com.example.pilotlogbook.data.validation.SignUp
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.activityNavController
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
            createAccount()
        }

        observeRegistrationValidation()

        observeState()

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

            if(it.name is ValidationResult.Success && it.lastName
                       is ValidationResult.Success && it.email
                       is ValidationResult.Success && it.password
                       is ValidationResult.Success && it.repeatPassword
                       is ValidationResult.Success){
                activityNavController().navigate(R.id.navigationViewFragment, null, navOptions {
                    popUpTo(R.id.mainRegisterFragment){
                        inclusive = true
                    }
                })
            }
        }
    }

    private fun observeState(){
        signUpViewModel.state.observe(viewLifecycleOwner){
            fillError(bindingClass.etEmail, it.emailErrorMessage)
            Log.d("MyTag5", "Progress - ${it.showProgress}")

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

}