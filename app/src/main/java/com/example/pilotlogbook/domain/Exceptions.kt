package com.example.pilotlogbook.domain

open class AppException: RuntimeException()

class AccountAlreadyExistException: AppException()