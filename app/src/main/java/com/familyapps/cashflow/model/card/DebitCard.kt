package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.Instant
import java.util.*

@Entity(tableName = "DEBIT_CARD")
class DebitCard : Card() {
    @ColumnInfo(name = "DEPOSIT_DATE")
    var depositDate: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherDebitCard = other as DebitCard

        return depositDate == otherDebitCard.depositDate
    }

    override fun hashCode(): Int {
        return Objects.hash(depositDate)
    }
}