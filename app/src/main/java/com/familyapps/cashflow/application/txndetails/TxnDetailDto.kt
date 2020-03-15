package com.familyapps.cashflow.application.txndetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Instant

@Parcelize
class TxnDetailDto (
    val txnId : Int
) : Parcelable {
    var transactionID : Int = this.txnId
}