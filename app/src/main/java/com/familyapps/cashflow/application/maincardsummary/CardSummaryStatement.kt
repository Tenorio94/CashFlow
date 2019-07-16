package com.familyapps.cashflow.application.maincardsummary

class CardSummaryStatement (cardImage: Int, cardSummary: String, cardName : String, cardNumber : String) {
    var cardImageResource : Int = cardImage
    var cardSummaryStatement : String = cardSummary
    var cardSummaryName : String = cardName
    var cardSummaryNumber : String = cardNumber

    init {
        this.cardImageResource = cardImage
        this.cardSummaryStatement = cardSummary
        this.cardSummaryName = cardName
        this.cardSummaryNumber = cardNumber
    }
}