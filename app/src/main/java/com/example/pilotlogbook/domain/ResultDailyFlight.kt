package com.example.pilotlogbook.domain

import java.lang.Exception


typealias Mapper<Input, Output> = (Input) -> Output

sealed class ResultDailyFlight<T> {

    fun <R> map(mapper: Mapper<T, R>? = null): ResultDailyFlight<R> = when(this){
        is LoadingResult -> LoadingResult()
        is ErrorResult -> ErrorResult(this.throwable)
        is SuccessResult -> {
            if(mapper == null) throw IllegalStateException("Mapper should not be NUll for success result")
            SuccessResult(mapper(this.data))
        }
    }

}

class LoadingResult<T>: ResultDailyFlight<T>()

class SuccessResult<T>(val data: T): ResultDailyFlight<T>()

class ErrorResult<T>(val throwable: Throwable): ResultDailyFlight<T>()


fun <T>ResultDailyFlight<T>?.takeSuccess(): T? {
      return if(this is SuccessResult)
          this.data
      else
          null
}


