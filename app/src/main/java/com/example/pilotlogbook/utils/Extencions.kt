package com.example.pilotlogbook.utils


import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.pilotlogbook.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.fragmentContainerView2)

fun <T>LiveData<T>.requireValue(): T {
    return this.value ?: throw IllegalStateException("Value is empty")
}

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}

