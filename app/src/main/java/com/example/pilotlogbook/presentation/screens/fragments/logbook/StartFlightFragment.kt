package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.Manifest
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
import androidx.core.content.ContextCompat
import com.example.pilotlogbook.databinding.FragmentStartFlightBinding
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFlightFragment : Fragment() {
    lateinit var bindingClass: FragmentStartFlightBinding
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentStartFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.btnStartFlight.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(requireContext(), "Permission success", Toast.LENGTH_SHORT).show()
            }else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }

        bindingClass.mapView.onCreate(savedInstanceState)

        bindingClass.mapView.getMapAsync{
            map = it
        }


    }

    override fun onStart() {
        super.onStart()
        bindingClass.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        bindingClass.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        bindingClass.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        bindingClass.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        bindingClass.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingClass.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bindingClass.mapView.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.all { it == PackageManager.PERMISSION_GRANTED}){
                Toast.makeText(requireContext(), "Permission success", Toast.LENGTH_SHORT).show()
            } else{
               if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                   AlertDialog.Builder(requireContext())
                       .setTitle("Permission denied")
                       .setMessage("To record the flight route, you need to grant access to geolocation")
                       .setPositiveButton("Grant permission"){ _, _ ->
                           requestPermissions(
                               arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                   Manifest.permission.ACCESS_COARSE_LOCATION),
                               LOCATION_PERMISSION_REQUEST_CODE
                           )
                       }
                       .create()
                       .show()
               }else {
                   askUserForOpeningAppSettings()
               }
            }
        }
    }

    private fun askUserForOpeningAppSettings() {
        val appSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", requireActivity().packageName, null))
        if(requireActivity().packageManager.resolveActivity(appSettings, PackageManager.MATCH_DEFAULT_ONLY) == null){
            Toast.makeText(requireContext(), "Permission denied forever", Toast.LENGTH_SHORT).show()
        }else{
            AlertDialog.Builder(requireContext())
                .setTitle("Permission denied")
                .setMessage("You have denied permission. " +
                "To record the flight route, you need to grant access to geolocation. " +
                "You can change your decision in app settings. \n\n" +
                "Would you like to open app settings?")
                .setPositiveButton("Open"){ _, _ ->
                    startActivity(appSettings)
                }
                .create()
                .show()
        }
    }

    private companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}