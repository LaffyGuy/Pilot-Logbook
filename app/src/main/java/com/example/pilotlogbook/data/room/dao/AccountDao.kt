package com.example.pilotlogbook.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pilotlogbook.data.room.entities.account.AccountEntity
import com.example.pilotlogbook.data.room.entities.tuples.AccountSignInTuple
import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.domain.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT id, password FROM account_table WHERE email = :email")
    suspend fun findAccountByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountEntity::class)
    suspend fun updateFistNameOrLastName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple)

    @Insert
    suspend fun createAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM account_table WHERE id = :id")
    fun findAccountById(id: Int): Flow<AccountEntity?>

}