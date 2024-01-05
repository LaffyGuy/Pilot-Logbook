package com.example.pilotlogbook.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE account_table ADD totalDailyFlightTime LONG  DEFAULT 0")
    }

}

val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE account_table ADD totalDailyFlightTime LONG DEFAULT 0")
    }

}

val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE account_table ADD COLUMN totalDailyFlightTime INTEGER DEFAULT 0")
    }

}

//val MIGRATION_12_13 = object : Migration(12,13) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("CREATE TABLE new_daily_flight_table (" +
//                "id: INTEGER PRIMARY KEY," +
//                "date: INTEGER NOT NULL," +
//                "")")
//    }
//
//}