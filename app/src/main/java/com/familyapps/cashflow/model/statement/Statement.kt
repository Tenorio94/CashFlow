package com.familyapps.cashflow.model.statement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "STATEMENT")
class Statement {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "STATEMENT_ID")
    var id: Int? = 0

    @ColumnInfo(name = "MONTH")
    var month: String = ""

    @ColumnInfo(name = "START_DATE")
    var startDate: Instant = Instant.now()

    @ColumnInfo(name = "END_DATE")
    var endDate: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherStatement = other as Statement

        return id == otherStatement.id
                && month == otherStatement.month
                && startDate == otherStatement.startDate
                && endDate == otherStatement.endDate
    }

    override fun hashCode(): Int {
        return Objects.hash(id, month, startDate, endDate)
    }
}