package com.example.pilotlogbook.presentation.screens.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentSignInBinding
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.presentation.viewmodels.registerviewmodel.SignInViewModel
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
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null, navOptions {
                popUpTo(R.id.mainRegisterFragment){
                    inclusive = true
                }
            })

        }

        bindingClass.btnLogin.setOnClickListener {
          signIn()
        }

        observeSignInValidation()

        observeId()

    }

    private fun signIn(){
        signInViewModel.signIn(bindingClass.etEmail.text.toString(), bindingClass.etPassword.text.toString())
    }

    private fun observeId(){
        signInViewModel.id.observe(viewLifecycleOwner){
            if(it != null){
              activityNavController().navigate(R.id.navigationViewFragment)
            }else{
                Toast.makeText(requireContext(), "Email or password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeSignInValidation(){
        signInViewModel.validation.observe(viewLifecycleOwner){
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
        }
    }


}
