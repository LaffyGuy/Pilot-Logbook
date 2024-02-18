package com.example.pilotlogbook.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//val MIGRATION_9_10 = object : Migration(9, 10) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("ALTER TABLE account_table ADD totalDailyFlightTime LONG  DEFAULT 0")
//    }
//
//}
//
//val MIGRATION_10_11 = object : Migration(10, 11) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("ALTER TABLE account_table ADD totalDailyFlightTime LONG DEFAULT 0")
//    }
//
//}
//
//val MIGRATION_11_12 = object : Migration(11, 12) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("ALTER TABLE account_table ADD COLUMN totalDailyFlightTime INTEGER DEFAULT 0")
//    }
//
//}

//val MIGRATION_OPERATIONAL_CONDITION_TIME_12_13 = object : Migration(12, 13){
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("CREATE TABLE IF NOT EXIST 'operational_condition_time_table_v2' (" +
//           "'operation_night' INTEGER," +
//           "'ifr' INTEGER)")
//
//        db.execSQL("INSERT INTO ")
//    }
//
//}
//
//val MIGRATION_DEPARTURE_12_13 = object : Migration(12, 13){
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("CREATE TABLE IF NOT EXIST 'operational_condition_time_table_v2' (" +
//                "'operation_night' INTEGER," +
//                "'ifr' INTEGER)")
//    }
//
//}

//val MIGRATION_12_13 = object : Migration(12, 13){
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS 'daily_flight_table_v2' " +
//                "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                "'date' INTEGER, " +
//                "'departure' INTEGER, " +
//                "'arrival' INTEGER, " +
//                "'aircraft' INTEGER, " +
//                "'singlePilotTime' INTEGER, " +
//                "'multiPilotTime' INTEGER, " +
//                "'totalTimeOffFlight' INTEGER, " +
//                "'picName' TEXT, " +
//                "'landings' INTEGER, " +
//                "'landings INTEGER', " +
//                "'operationalConditionTime', " +
//                "`pilotFunctionTime` INTEGER, " +
//                "`syntheticTrainingDevicesSession` INTEGER, " +
//                "`remarksAndEndorsements` TEXT)")
//
//        db.execSQL("INSERT INTO daily_flight_table_v2 " +
//                "SELECT id, date, departure.place AS departure_place, departure.time AS departure_time, arrival.place, arrival.time, " +
//                "aircraft.model, aircraft.registration, CAST(se AS INTEGER) AS se, CAST(me AS INTEGER) AS me, " +
//                "CAST(multiPilotTime AS INTEGER), totalTimeOffFlight, picName, " +
//                "landings, CAST(night AS INTEGER) AS night, CAST(ifr AS INTEGER) AS ifr, " +
//                "CAST(pilotInComand AS INTEGER) AS pilotInComand, CAST(coPilot AS INTEGER) AS coPilot, " +
//                "CAST(dual AS INTEGER) AS dual, CAST(instructor AS INTEGER) AS instructor, " +
//                "CAST(totalTimeOfSession AS INTEGER) AS totalTimeOfSession, remarksAndEndorsements " +
//                "FROM daily_flight_table"
//        )
//
//        db.execSQL("DROP TABLE daily_flight_table")
//
//        db.execSQL("ALTER TABLE daily_flight_table_v2 RENAME TO daily_flight_table")
//
//    }
//
//}

val MIGRATION_15_16 = object : Migration(15, 16){
    override fun migrate(db: SupportSQLiteDatabase) {
       db.execSQL("ALTER TABLE account_table ADD imagePath TEXT")
    }

}