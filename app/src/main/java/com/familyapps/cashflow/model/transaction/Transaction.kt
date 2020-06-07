package com.familyapps.cashflow.model.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "TRANSACTION")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TRANSACTION_ID")
    var id: Int?,

    @ColumnInfo(name = "DESCRIPTION")
    var description: String?,

    @ColumnInfo(name = "STORE")
    var store: String,

    @ColumnInfo(name = "AMOUNT")
    var amount: Double,

    @ColumnInfo(name = "DIFERRED_FLAG")
    var diferredFlag: Boolean,

    @ColumnInfo(name = "DIFERRED_TIME")
    var differedTime: String?,

    @ColumnInfo(name = "AMOUNT_PER_MONTH")
    var amountPerMonth: Double?,

    @ColumnInfo(name = "TXN_DATE")
    var txnDate: Instant,

    @ColumnInfo(name = "CARD_NUMBER")
    var cardNumber: String,

    @ColumnInfo(name = "ACCOUNT_NUMBER")
    var accountNumber: String,

    @ColumnInfo(name = "ORIGINAL_TXN_DATE")
    var originalTxnDate: Instant
)