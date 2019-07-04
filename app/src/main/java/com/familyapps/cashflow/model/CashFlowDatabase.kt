package com.familyapps.cashflow.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.familyapps.cashflow.infraestructure.InstantConverter
import com.familyapps.cashflow.model.account.Account
import com.familyapps.cashflow.model.bank.Bank
import com.familyapps.cashflow.model.card.CreditCard
import com.familyapps.cashflow.model.card.DebitCard
import com.familyapps.cashflow.model.statement.Statement
import com.familyapps.cashflow.model.transaction.Transaction
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository

@Database(
    entities = arrayOf(
        User::class,
        Account::class,
        Bank::class,
        CreditCard::class,
        DebitCard::class,
        Statement::class,
        Transaction::class
    ), version = 1, exportSchema = false
)

@TypeConverters(InstantConverter::class)
abstract class CashFlowDatabase : RoomDatabase() {
    abstract fun userRepository(): UserRepository


    companion object {
        private var INSTANCE: CashFlowDatabase? = null

        fun getInstance(context: Context): CashFlowDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, CashFlowDatabase::class.java, "cashflow-database").build()
    }
}