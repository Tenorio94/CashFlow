package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import java.time.Instant

@Entity(tableName = "CREDIT_CARD", indices = arrayOf(Index(value = ["CARD_NUMBER", "ACCOUNT_NUMBER"])))
data class CreditCard(
    @Embedded
    var cardData : Card,

    @ColumnInfo(name = "CUTOFF_DATE")
    var cutOffDate: Instant,

    @ColumnInfo(name = "EXPENSE_LIMIT")
    var expenseLimit: Double,

    @ColumnInfo(name = "AVAILABLE_CREDIT")
    var availableCredit: Double,

    @ColumnInfo(name = "ANNUAL_FEE")
    var annualFee: Double,

    @ColumnInfo(name = "ANNUAL_FEE_PLUS_TAX")
    var annualFeePlusTax: Double
)