package com.familyapps.cashflow.infraestructure

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import java.time.Instant

private val logTag = "CF_SM_CREDENTIALS"

fun setSession(applicationContext: Context, emailTxt: String, passwordTxt: String, creationDate: Instant) {
    val userSession = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val credentials: SharedPreferences.Editor = userSession.edit()
    credentials.putString("userName", emailTxt)
    credentials.putString("password", passwordTxt)
    credentials.putLong("sessionDate", creationDate.epochSecond)
    credentials.apply()
}

fun getSession(applicationContext: Context, creationDate: Instant): String {
    val userSession = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val createdSession = Instant.ofEpochSecond(userSession.getLong("sessionDate", -1))
    val userName = userSession.getString("userName", "")
    Log.i(logTag, String.format("Getting session for username: %s", userName))

    if (creationDate > createdSession.plusSeconds(2592000)) {
        val userSession = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val credentials: SharedPreferences.Editor = userSession.edit()

        credentials.remove("userName")
        credentials.remove("password")
        credentials.remove("sessionDate")
        credentials.apply()

        Toast.makeText(applicationContext, "Session no longer valid, please login again", Toast.LENGTH_SHORT).show()
        return ""
    }

    return userName!!
}

fun deleteSession(applicationContext: Context) {
    val userSession = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val credentials: SharedPreferences.Editor = userSession.edit()

    credentials.remove("userName")
    credentials.remove("password")
    credentials.remove("sessionDate")
    credentials.apply()

    Toast.makeText(applicationContext, "User logged out successfully", Toast.LENGTH_SHORT).show()
}