package com.familyapps.cashflow.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "USER", indices = arrayOf(Index(value = ["EMAIL"], unique = true)))
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "USER_ID")
    var id: Long,

    @ColumnInfo(name = "FIRST_NAME")
    var firstName: String,

    @ColumnInfo(name = "LAST_NAME")
    var lastName: String,

    @ColumnInfo(name = "SECOND_LAST_NAME")
    var secondLastName: String?,

    @ColumnInfo(name = "EMAIL")
    var email: String,

    @ColumnInfo(name = "PASSWORD")
    var password: String,

    @ColumnInfo(name = "AGE")
    var age: Int? = 0,

    @ColumnInfo(name = "ADDRESS")
    var address: String? = ""
)