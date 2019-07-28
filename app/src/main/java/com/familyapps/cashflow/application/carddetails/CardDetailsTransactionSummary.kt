package com.familyapps.cashflow.application.carddetails

import java.time.Instant

class CardDetailsTransactionSummary(_txnMonthAmount: Double, _txnDate: Instant) {
    var txnMonthAmount : Double = _txnMonthAmount
    var txnDate : Instant = _txnDate

    init {
        this.txnMonthAmount = _txnMonthAmount
        this.txnDate = _txnDate
    }
}