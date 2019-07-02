package com.familyapps.cashflow.application.carddetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CardDetailDTO(val mainCardName : String, val mainCardStatement : String, val mainCardImage: Int) : Parcelable{
    var cardName : String = mainCardName
    var cardStatement : String = mainCardStatement
    var cardImage : Int = mainCardImage
}