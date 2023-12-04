package com.example.pilotlogbook.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.pilotlogbook.R

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.fragmentContainerView2)

fun <T>LiveData<T>.requireValue(): T {
    return this.value ?: throw IllegalStateException("Value is empty")
}