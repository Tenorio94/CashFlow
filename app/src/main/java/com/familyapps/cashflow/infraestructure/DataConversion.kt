package com.familyapps.cashflow.infraestructure

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


fun convertDoubleToCash(doubleToConvert: Double) : String{
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    return currencyFormatter.format(doubleToConvert)
}

fun convertInstantToString(instantToConvert: Instant) : String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        .withLocale(Locale.ENGLISH)
        .withZone(systemDefault())

    return formatter.format(instantToConvert)
}