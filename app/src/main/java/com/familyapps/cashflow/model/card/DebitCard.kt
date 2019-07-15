package com.familyapps.cashflow.model.card

import androidx.room.*
import java.time.Instant

@Entity(tableName = "DEBIT_CARD", indices = arrayOf(Index(value = ["CARD_NUMBER", "ACCOUNT_NUMBER"])))
class DebitCard (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CARD_ID")
    var id: Int?,

    @ColumnInfo(name = "BANK_CARD_NAME")
    var bankCardName: String,

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
    var accountNumber: String,

    @ColumnInfo(name = "DEPOSIT_DATE")
    var depositDate: Instant
)