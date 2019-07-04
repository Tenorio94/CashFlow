package com.familyapps.cashflow.model.bank

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "BANK")
class Bank {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BANK_ID")
    var id : Int? = 0

    @ColumnInfo(name = "BANK_NAME")
    var bankName : String = ""

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherBank = other as Bank

        return id == otherBank.id
                && bankName == otherBank.bankName
    }

    override fun hashCode(): Int {
        return Objects.hash(id, bankName)
    }
}