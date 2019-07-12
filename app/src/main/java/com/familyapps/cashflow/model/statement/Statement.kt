package com.familyapps.cashflow.model.statement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "STATEMENT", indices = arrayOf(Index(value = ["CARD_NUMBER", "STATEMENT_ID"])))
class Statement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "STATEMENT_ID")
    var id: Int?,

    @ColumnInfo(name = "MONTH")
    var month: String,

    @ColumnInfo(name = "START_DATE")
    var startDate: Instant,

    @ColumnInfo(name = "END_DATE")
    var endDate: Instant,

    @ColumnInfo(name = "CARD_NUMBER")
    var cardNumber: String
)