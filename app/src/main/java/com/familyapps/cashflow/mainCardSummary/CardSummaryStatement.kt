package com.familyapps.cashflow.mainCardSummary

class CardSummaryStatement (cardImage: Int, cardSummary: String, cardName : String) {
    var cardImageResource : Int = cardImage
    var cardSummaryStatement : String = cardSummary
    var cardSummaryName : String = cardName

    init {
        this.cardImageResource = cardImage
        this.cardSummaryStatement = cardSummary
        this.cardSummaryName = cardName
    }
}