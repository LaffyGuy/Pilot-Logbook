package com.example.pilotlogbook.domain

import java.lang.Exception


typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T> {

    fun <R> map(mapper: Mapper<T, R>? = null): Result<R> = when(this){
        is LoadingResult -> LoadingResult()
        is ErrorResult -> ErrorResult(this.exception)
        is SuccessResult -> {
            if(mapper == null) throw IllegalStateException("Mapper should not be NUll for success result")
            SuccessResult(mapper(this.data))
        }
    }

}

class LoadingResult<T>: Result<T>()

class SuccessResult<T>(val data: T): Result<T>()

class ErrorResult<T>(val exception: Exception): Result<T>()


fun <T>Result<T>?.takeSuccess(): T? {
      return if(this is SuccessResult)
          this.data
      else
          null
}


