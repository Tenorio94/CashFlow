package com.familyapps.cashflow.application.carddetails


class CardDetailsTransactionSummary(_txnMonthAmount: String, _txnDate: String, _txnDescription: String) {
    var txnMonthAmount : String = _txnMonthAmount
    var txnDate : String = _txnDate
    var txnDescription: String = _txnDescription

    init {
        this.txnMonthAmount = _txnMonthAmount
        this.txnDate = _txnDate
        this.txnDescription = _txnDescription
    }
}