package com.example.pilotlogbook.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pilotlogbook.data.room.dao.AccountDao
import com.example.pilotlogbook.data.room.dao.DailyFlightDao
import com.example.pilotlogbook.data.room.entities.account.AccountEntity
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity

@Database(entities = [DailyFlightEntity::class, AccountEntity::class], version = 2)
abstract class PilotLogBookDataBase: RoomDatabase() {

    abstract fun getDailyFlightDao(): DailyFlightDao

    abstract fun getAccountDao(): AccountDao

}