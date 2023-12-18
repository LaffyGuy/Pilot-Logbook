package com.example.pilotlogbook.data.repositories

import android.database.sqlite.SQLiteConstraintException
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.data.room.entities.account.AccountEntity
import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.domain.entities.Account
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.SignUp
import com.example.pilotlogbook.domain.AccountAlreadyExistException
import com.example.pilotlogbook.domain.AuthEmailException
import com.example.pilotlogbook.domain.AuthPasswordException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.Field
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRealisation(private val db: PilotLogBookDataBase, private val appSettings: AppSettings): AccountRepository {

    override suspend fun isSignedIn(): Boolean {
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun findAccountByEmailAndPassword(email: String, password: String): Int {
        if(email.isBlank()) throw EmptyFieldException(Field.Email)
        if(password.isBlank()) throw EmptyFieldException(Field.Password)
        delay(1000)
        val tuple = db.getAccountDao().findAccountByEmail(email) ?: throw AuthEmailException()
        if(tuple.password != password)  throw AuthPasswordException()
        return tuple.id
    }

    override suspend fun updateFistNameOrLastName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple) {
          db.getAccountDao().updateFistNameOrLastName(accountUpdateUsernameTuple)
    }

    override suspend fun signUp(signUp: SignUp) {
        signUp.validate()
        delay(1000)
        createAccount(signUp)
    }

    private suspend fun createAccount(signUp: SignUp) {
         try {
             val entity = AccountEntity.fromSignUp(signUp)
             db.getAccountDao().createAccount(entity)
             val accountId = findAccountByEmailAndPassword(signUp.email, signUp.password)
             appSettings.setCurrentAccountId(accountId)
         }catch (e: SQLiteConstraintException){
             val appException = AccountAlreadyExistException()
             appException.initCause(e)
             throw appException
         }
    }

    override fun findAccountById(id: Int): Flow<Account?> {
        return db.getAccountDao().findAccountById(id).map { accountEntity -> accountEntity?.toAccount() }
    }

    override suspend fun logOut() {
        delay(1000)
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
    }
}