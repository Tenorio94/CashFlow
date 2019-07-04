package com.familyapps.cashflow.model.card

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.Instant
import java.util.*

@Entity(tableName = "CREDIT_CARD")
class CreditCard : Card() {
    @ColumnInfo(name = "CUTOFF_DATE")
    var cutOffDate: Instant = Instant.now()

    @ColumnInfo(name = "EXPENSE_LIMIT")
    var expenseLimit: Double = 0.0

    @ColumnInfo(name = "AVAILABLE_CREDIT")
    var availableCredit: Double = 0.0

    @ColumnInfo(name = "ANNUAL_FEE")
    var annualFee: Double = 0.0

    @ColumnInfo(name = "ANNUAL_FEE_PLUS_TAX")
    var annualFeePlusTax: Double = 0.0

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        if (javaClass != other.javaClass)
            return false

        val otherCreditCard = other as CreditCard

        return cutOffDate == otherCreditCard.cutOffDate
                && expenseLimit == otherCreditCard.expenseLimit
                && availableCredit == otherCreditCard.availableCredit
                && annualFee == otherCreditCard.annualFee
                && annualFeePlusTax == otherCreditCard.annualFeePlusTax
    }

    override fun hashCode(): Int {
        return Objects.hash(cutOffDate, expenseLimit, availableCredit, annualFee, annualFeePlusTax)
    }
}