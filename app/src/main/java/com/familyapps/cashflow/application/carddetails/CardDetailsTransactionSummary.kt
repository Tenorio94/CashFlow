package com.familyapps.cashflow.application.carddetails


class CardDetailsTransactionSummary(_txnMonthAmount: String, _txnDate: String, _txnDescription: String, _txnNumber: String) {
    var txnMonthAmount : String = _txnMonthAmount
    var txnDate : String = _txnDate
    var txnDescription: String = _txnDescription
    var txnNumber: String = _txnNumber

    init {
        this.txnMonthAmount = _txnMonthAmount
        this.txnDate = _txnDate
        this.txnDescription = _txnDescription
        this.txnNumber = _txnNumber
    }
}