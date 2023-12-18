package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentSignUpBinding
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

        observeState()

        observeNavigate()

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

    private fun observeNavigate(){
        signUpViewModel.navigate.observe(viewLifecycleOwner){
            if(it){
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
            fillError(bindingClass.etName, it.firstNameErrorMessage)
            fillError(bindingClass.etLastName, it.lastNameErrorMessage)
            fillError(bindingClass.etEmail, it.emailErrorMessage)
            fillError(bindingClass.etPassword, it.passwordErrorMessage)
            fillError(bindingClass.etRepeatPassword, it.repeatPasswordErrorMessage)

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