package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.Instant

open class Card (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CARD_ID")
    var id: Int?,

    @ColumnInfo(name = "CARD_NAME")
    var cardName: String,

    @ColumnInfo(name = "CARD_NUMBER")
    var cardNumber: String,

    @ColumnInfo(name = "EXPIRATION_DATE")
    var expirationDate: Instant,

    @ColumnInfo(name = "ISSUED_DATE")
    var issuedDate: Instant,

    @ColumnInfo(name = "TYPE")
    var cardType: String = "DEBIT",

    @ColumnInfo(name = "ISSUING_BANK")
    var issuingBank: String,

    @ColumnInfo(name = "ACCOUNT_NUMBER")
    var accountNumber: String

)