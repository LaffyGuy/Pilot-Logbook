package com.example.pilotlogbook.presentation.screens.fragments.logbook.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.databinding.FragmentEditProfileBinding
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var bindingClass: FragmentEditProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val profileArgs by navArgs<EditProfileFragmentArgs>()
    private var imagePath: String? = null

    private val mediaPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotMediaPermissionResult
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentEditProfileBinding.inflate(layoutInflater)
        return bindingClass.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfoByAccount()

        bindingClass.editPersonAvatar.setOnClickListener {
            mediaPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        bindingClass.btnEditProfile.setOnClickListener {
            editProfileInfo()
        }

    }

    private fun getInfoByAccount(){
        val data = profileArgs.profileInfo
        bindingClass.etFirstName.setText(data.firstName)
        bindingClass.etLastName.setText(data.lastName)
        Glide.with(requireContext()).load(data.imagePath).placeholder(R.drawable.ic_add_flight_person).into(bindingClass.editPersonAvatar)


    }

    private fun editProfileInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            val accountEditInfo = AccountUpdateUsernameTuple(
                profileArgs.profileInfo.id,
                bindingClass.etFirstName.text.toString(),
                bindingClass.etLastName.text.toString(),
                imagePath.toString()
            )
            profileViewModel.updateFirstNameOrLastName(accountEditInfo)
        }
        findNavController().popBackStack()
    }

    private fun askUserForOpeningAppSettings(){
        val appSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", requireActivity().packageName, null))
        if(requireActivity().packageManager.resolveActivity(appSettings, PackageManager.MATCH_DEFAULT_ONLY) == null){
            Toast.makeText(requireContext(), "Permission denied forever", Toast.LENGTH_SHORT).show()
        }else{
            AlertDialog.Builder(requireContext())
                .setTitle("Permission denied")
                .setMessage("You have denied permission. " +
                        "To change the profile image, you need to grant access to read media store. " +
                        "You can change your decision in app settings. \n\n" +
                        "Would you like to open app settings?")
                .setPositiveButton("Open"){ _, _ ->
                    startActivity(appSettings)
                }
                .create()
                .show()
        }
    }

    private fun onGotMediaPermissionResult(granted: Boolean){
        if(granted){
            getPhotoFromGallery()
        } else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder(requireContext())
                    .setTitle("Permission denied")
                    .setMessage("To change the profile image, you need to grant access to read media store")
                    .setPositiveButton("Grant permission"){ _, _ ->
                    }
                    .create()
                    .show()
            }else {
                askUserForOpeningAppSettings()
            }
        }
    }

    private fun getPhotoFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, INTENT_FOR_READ_MEDIA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == INTENT_FOR_READ_MEDIA_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Glide.with(this).load(data.dataString).into(bindingClass.editPersonAvatar)
            imagePath = data.dataString
        }

    }

    companion object {
        private const val INTENT_FOR_READ_MEDIA_REQUEST_CODE = 0
    }


}