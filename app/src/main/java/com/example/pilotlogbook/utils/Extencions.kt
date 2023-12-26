package com.example.pilotlogbook.utils

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.pilotlogbook.R
import com.example.pilotlogbook.domain.ErrorResult
import com.example.pilotlogbook.domain.LoadingResult
import com.example.pilotlogbook.domain.Result
import com.example.pilotlogbook.domain.SuccessResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan
import java.lang.Exception

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.fragmentContainerView2)

fun <T>LiveData<T>.requireValue(): T {
    return this.value ?: throw IllegalStateException("Value is empty")
}

fun <T>pendingResult(root: ViewGroup, result: Result<T>, onLoading: () -> Unit,
                              onSuccess: (T) -> Unit, onError: (Exception) -> Unit  ){

    root.children.forEach { it.visibility = View.GONE }
    when(result){
        is LoadingResult -> onLoading()
        is SuccessResult -> onSuccess(result.data)
        is ErrorResult -> onError(result.exception)
    }

}

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}

