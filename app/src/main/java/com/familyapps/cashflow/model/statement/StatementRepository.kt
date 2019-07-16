package com.familyapps.cashflow.model.statement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StatementRepository {

    @Insert
    fun insertStatement(statement: Statement)

    @Insert
    fun insertBatchStatement(statements: List<Statement>)

    @Update
    fun updateStatement(statement: Statement)

    @Query("SELECT * FROM STATEMENT WHERE MONTH = :month")
    fun findStatementByMonth(month: String) : Statement

    @Query("SELECT * FROM STATEMENT WHERE CARD_NUMBER = :cardNumber")
    fun findStatementsByCardNumber(cardNumber: String) : List<Statement>

    @Query("SELECT * FROM STATEMENT WHERE MONTH = :month AND STATEMENT_EMAIL = :email")
    fun findStatementsByMonthAndEmail(month: String, email: String) : List<Statement>
}