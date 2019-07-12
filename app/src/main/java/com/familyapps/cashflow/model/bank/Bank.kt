package com.familyapps.cashflow.model.bank

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BANK", indices = arrayOf(Index(value = ["BANK_NAME"], unique = true)))
data class Bank (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BANK_ID")
    var id : Int?,

    @ColumnInfo(name = "BANK_NAME")
    var bankName : String
)