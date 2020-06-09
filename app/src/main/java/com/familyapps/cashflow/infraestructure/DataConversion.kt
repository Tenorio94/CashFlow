package com.familyapps.cashflow.infraestructure

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Function used to convert a number into a String, this contains the traditional format of when we
 * manage currencies. This is used to display the information on the UI
 *
 * @param doubleToConvert - Double containing the number fetched from the database of a transaction.
 *
 * @return String - Double formatted into a String in format $X,XXX,XXX.XX
 */
fun convertDoubleToCash(doubleToConvert: Double) : String{
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    return currencyFormatter.format(doubleToConvert)
}

/**
 * Function used to convert an Instant date into a readable String, this is used in WW format which
 * includes the DD-MM-YYYY format.
 *
 * @param instantToConvert - Instant fetched from the database to convert.
 *
 * @return String - Converted Date as in format DD-MM-YYYY
 */
fun convertInstantToString(instantToConvert: Instant) : String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        .withLocale(Locale.ENGLISH)
        .withZone(systemDefault())

    return formatter.format(instantToConvert)
}