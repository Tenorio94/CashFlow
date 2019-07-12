package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import java.time.Instant

@Entity(tableName = "DEBIT_CARD", indices = arrayOf(Index(value = ["CARD_NUMBER", "ACCOUNT_NUMBER"])))
class DebitCard (
    @Embedded
    var cardData : Card,

    @ColumnInfo(name = "DEPOSIT_DATE")
    var depositDate: Instant
)