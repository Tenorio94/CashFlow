package com.familyapps.cashflow.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ACCOUNT")
class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ACCOUNT_ID")
    var id : Int? = 0

    @ColumnInfo(name = "ACCOUNT_NUMBER")
    var accountNumber : Int = 0

    @ColumnInfo(name = "BRANCH")
    var accountBranch : String = ""

    @ColumnInfo(name = "TYPE")
    var accountType : String = ""

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherAccount = other as Account

        return id == otherAccount.id
                && accountNumber == otherAccount.accountNumber
                && accountBranch == otherAccount.accountBranch
                && accountType == otherAccount.accountType
    }

    override fun hashCode(): Int {
        return Objects.hash(id, accountNumber, accountBranch, accountType)
    }
}