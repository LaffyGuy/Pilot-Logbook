package com.example.pilotlogbook.domain.settings

import android.content.Context

class SharedPreferencesAppSettings(appContext: Context): AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    override fun getCurrentAccountId(): Int = sharedPreferences.getInt(PREF_CURRENT_ACCOUNT_ID, AppSettings.NO_ACCOUNT_ID)

    override fun setCurrentAccountId(accountId: Int) {
        sharedPreferences.edit().putInt(PREF_CURRENT_ACCOUNT_ID, accountId).apply()
    }

    companion object {
        const val PREF_CURRENT_ACCOUNT_ID = "accountId"
    }


}