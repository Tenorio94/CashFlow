package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

open class Card {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CARD_ID")
    var id: Int? = null

    @ColumnInfo(name = "CARD_NAME")
    var cardName: String = ""

    @ColumnInfo(name = "CARD_NUMBER")
    var cardNumber: String = ""

    @ColumnInfo(name = "EXPIRATION_DATE")
    var expirationDate: Instant = Instant.now()

    @ColumnInfo(name = "ISSUED_DATE")
    var issuedDate: Instant = Instant.now()

    @ColumnInfo(name = "TYPE")
    var cardType: String = "DEBIT"

    @ColumnInfo(name = "ISSUING_BANK")
    var issuingBank: String = ""

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherCard = other as Card

        return id == otherCard.id
                && cardName == otherCard.cardName
                && cardNumber == otherCard.cardNumber
                && expirationDate == otherCard.expirationDate
                && issuedDate == otherCard.issuedDate
                && cardType == otherCard.cardType
                && issuingBank == otherCard.issuingBank
    }

    override fun hashCode(): Int {
        return Objects.hash(id, cardName, cardNumber, expirationDate, issuedDate, cardType, issuingBank)
    }
}