package com.familyapps.cashflow.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ACCOUNT",
    indices = arrayOf(Index(value = ["ACCOUNT_NUMBER", "TYPE"], unique = true))
)
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ACCOUNT_ID")
    var id: Int?,

    @ColumnInfo(name = "ACCOUNT_NUMBER")
    var accountNumber: Int,

    @ColumnInfo(name = "BRANCH")
    var accountBranch: String,

    @ColumnInfo(name = "TYPE")
    var accountType: String,

    @ColumnInfo(name = "ACCOUNT_EMAIL")
    var accountEmail: String

)