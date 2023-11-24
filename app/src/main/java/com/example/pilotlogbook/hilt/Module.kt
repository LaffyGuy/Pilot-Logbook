package com.example.pilotlogbook.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pilotlogbook.data.repositories.AccountRealisation
import com.example.pilotlogbook.data.repositories.DailyFlightRealisation
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.SharedPreferencesAppSettings
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
    ).fallbackToDestructiveMigration()
     .build()




}