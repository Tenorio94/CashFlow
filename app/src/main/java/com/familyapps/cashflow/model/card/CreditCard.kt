package com.familyapps.cashflow.model.card

import androidx.room.*
import java.time.Instant

@Entity(
    tableName = "CREDIT_CARD",
    indices = arrayOf(Index(value = ["CARD_NUMBER", "ACCOUNT_NUMBER"]), Index(value = ["CARD_NUMBER", "CARD_EMAIL"]))
)
data class CreditCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CARD_ID")
    var id: Int?,

    @ColumnInfo(name = "CARD_NAME")
    var cardName: String,

    @ColumnInfo(name = "BANK_CARD_NAME")
    var bankCardName: String,

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

    @ColumnInfo(name = "CARD_EMAIL")
    var cardEmail: String,

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