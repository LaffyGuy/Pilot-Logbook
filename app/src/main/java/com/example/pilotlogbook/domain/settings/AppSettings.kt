package com.example.pilotlogbook.domain.settings

interface AppSettings {

    fun getCurrentAccountId(): Int

    fun setCurrentAccountId(accountId: Int)

    companion object {
        const val NO_ACCOUNT_ID = -1
    }

}