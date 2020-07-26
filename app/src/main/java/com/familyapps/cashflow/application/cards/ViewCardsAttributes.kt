package com.familyapps.cashflow.application.cards

class ViewCardsAttributes(cardImage: Int, cardNumber: String, cardName: String) {
    var viewCardImageResource: Int = cardImage
    var viewCardNumber: String = cardNumber
    var viewCardCardName: String = cardName

    /**
     * Constructor for the Cards Attributes that will be used to populate the Recycler View for
     * the View Cards Activity.
     */
    init {
        this.viewCardImageResource = cardImage
        this.viewCardNumber = cardNumber
        this.viewCardCardName = cardName
    }

    /**
     * ToString function used to print the Object as a readable element that can be printed out
     * with a simple To String.
     *
     * @return String containing Readable elements of the object.
     */
    override fun toString(): String {
        var stringBuilder: StringBuilder = StringBuilder()

        stringBuilder.append(
            String.format(
                "View Card Attributes \n Card Number: %s \n Card Name: %s \n Card Image: %d",
                this.viewCardNumber,
                this.viewCardCardName,
                this.viewCardImageResource
            )
        )

        return stringBuilder.toString()
    }
}