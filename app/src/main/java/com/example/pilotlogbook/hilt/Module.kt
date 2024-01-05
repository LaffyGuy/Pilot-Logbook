package com.example.pilotlogbook.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pilotlogbook.data.repositories.AccountRealisation
import com.example.pilotlogbook.data.repositories.DailyFlightRealisation
import com.example.pilotlogbook.data.room.MIGRATION_10_11
import com.example.pilotlogbook.data.room.MIGRATION_11_12
import com.example.pilotlogbook.data.room.MIGRATION_9_10
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.SharedPreferencesAppSettings
import com.example.pilotlogbook.utils.SelectDate
import com.example.pilotlogbook.utils.SelectTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideSelectTime(context: Context): SelectTime {
        return SelectTime(context)
    }

    @Provides
    @Singleton
    fun provideSelectDate(context: Context) : SelectDate {
        return  SelectDate(context)
    }

    @Provides
    @Singleton
    fun provideAppSettings(appContext: Context): AppSettings {
        return SharedPreferencesAppSettings(appContext)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(db: PilotLogBookDataBase, appSettings: AppSettings): AccountRepository {
        return AccountRealisation(db, appSettings)
    }

    @Provides
    @Singleton
    fun provideDailyFlightRepository(db: PilotLogBookDataBase): DailyFlightRepository {
        return DailyFlightRealisation(db)
    }

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun providePilotLogBookDataBase(app: Application) = Room.databaseBuilder(
        app, PilotLogBookDataBase::class.java, "pilot.db"
    ).addMigrations(MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12)
        .build()




}