package com.example.pilotlogbook.data.room.entities.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.domain.entities.Account
import com.example.pilotlogbook.data.validation.SignUp

@Entity(
    tableName = "account_table",
    indices = [
       Index("email", unique = true)
    ],
//    foreignKeys = [
//        ForeignKey(
//            entity = DailyFlightEntity::class,
//            parentColumns = ["totalTimeOffFlight"],
//            childColumns = ["totalDailyFlightTime"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE)val email: String,
    val password: String,
    val createAt: Long,
    val totalDailyFlightTime: Long = 0
) {
    fun toAccount(): Account = Account(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        createAt = createAt,
        totalDailyFlightTime = totalDailyFlightTime
    )

    companion object {
        fun fromSignUp(signUp: SignUp): AccountEntity = AccountEntity(
            id = 0,
            firstName = signUp.firstName,
            lastName = signUp.lastName,
            email = signUp.email,
            password = signUp.password,
            createAt = System.currentTimeMillis(),
            totalDailyFlightTime = 0
        )
    }

}
