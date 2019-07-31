package com.familyapps.cashflow.model.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionRepository {

    @Insert
    fun insertTransaction(transaction: Transaction)

    @Insert
    fun insertBatchTransaction(transactions: List<Transaction>)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM `TRANSACTION` WHERE CARD_NUMBER = :cardNumber")
    fun findTransactionsByCardNumber(cardNumber: String) : List<Transaction>

    @Query("SELECT * FROM `TRANSACTION` WHERE TRANSACTION_ID = :txnId")
    fun findTransactionByTxnId(txnId: Int) : Transaction

}