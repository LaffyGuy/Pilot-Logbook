package com.example.pilotlogbook.domain

import com.example.pilotlogbook.domain.settings.AddDailyFlightField
import com.example.pilotlogbook.domain.settings.Field


open class AppException: RuntimeException()

class EmptyFieldException(val field: Field): AppException()

class AccountAlreadyExistException: AppException()

class AuthEmailException: AppException()

class AuthPasswordException: AppException()

class PasswordMismatchException: AppException()

class EmailPatternException: AppException()

class PasswordLengthException: AppException()

class EmptyAddFlightFieldException(val field: AddDailyFlightField): AppException()

class NotValidInputTimeException(val field: AddDailyFlightField): AppException()