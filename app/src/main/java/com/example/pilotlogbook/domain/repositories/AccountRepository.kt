package com.example.pilotlogbook.domain.repositories

import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.domain.entities.Account
import com.example.pilotlogbook.data.validation.SignUp
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun isSignedIn(): Boolean

    suspend fun findAccountByEmailAndPassword(email: String, password: String): Int

    suspend fun updateFistNameOrLastName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple)

    suspend fun signUp(signUp: SignUp)

    fun findAccountById(id: Int): Flow<Account?>

    suspend fun logOut()

}