package com.familyapps.cashflow.model.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "TRANSACTION")
class Transaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TRANSACTION_ID")
    var id: Int? = 0

    @ColumnInfo(name = "DESCRIPTION")
    var description: String? = ""

    @ColumnInfo(name = "STORE")
    var store: String = ""

    @ColumnInfo(name = "AMOUNT")
    var amount: Double = 0.0

    @ColumnInfo(name = "DIFERRED_FLAG")
    var diferredFlag: Boolean = false

    @ColumnInfo(name = "DIFERRED_TIME")
    var differedTime: String? = ""

    @ColumnInfo(name = "AMOUNT_PER_MONTH")
    var amountPerMonth: Double? = 0.0

    @ColumnInfo(name = "TXN_DATE")
    var txnDate: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherTransaction = other as Transaction

        return id == otherTransaction.id
                && description == otherTransaction.description
                && amount == otherTransaction.amount
                && diferredFlag == otherTransaction.diferredFlag
                && differedTime == otherTransaction.differedTime
                && amountPerMonth == otherTransaction.amountPerMonth
                && txnDate == otherTransaction.txnDate
    }

    override fun hashCode(): Int {
        return Objects.hash(id, description, store, amount, diferredFlag, differedTime, amountPerMonth, txnDate)
    }
}