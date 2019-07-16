package com.familyapps.cashflow.infraestructure

import java.text.NumberFormat
import java.util.*

fun convertDoubleToCash(doubleToConvert: Double) : String{
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    return currencyFormatter.format(doubleToConvert)
}