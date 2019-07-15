package com.familyapps.cashflow.model.card

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardRepository {

    @Insert
    fun insertNewCreditCard(creditCard: CreditCard)

    @Insert
    fun insertNewDebitCard(debitCard: DebitCard)

    @Insert
    fun insertBatchCreditCard(creditCard: List<CreditCard>)

    @Update
    fun updateCreditCard(creditCard: CreditCard)

    @Update
    fun updateDebitCard(debitCard: DebitCard)

    @Query("SELECT * FROM CREDIT_CARD WHERE CARD_NUMBER = :cardNumber")
    fun findCreditCardByCardNumber(cardNumber: String): CreditCard

    @Query("SELECT * FROM DEBIT_CARD WHERE CARD_NUMBER = :cardNumber")
    fun findDebitCardByCardNumber(cardNumber: String): DebitCard

    @Query("SELECT * FROM CREDIT_CARD WHERE ACCOUNT_NUMBER = :accountNumber")
    fun findCreditCardByAccountNumber(accountNumber: String): CreditCard

    @Query("SELECT * FROM DEBIT_CARD WHERE ACCOUNT_NUMBER = :accountNumber")
    fun findDebitByAccountNumber(accountNumber: String): DebitCard

    @Query("SELECT * FROM CREDIT_CARD WHERE CARD_EMAIL = :email")
    fun findAllCreditCardsByEmail(email: String): List<CreditCard>

    @Query("DELETE FROM DEBIT_CARD WHERE CARD_NUMBER = :cardNumber")
    fun deleteDebitCardByCardNumber(cardNumber: String): Int

    @Query("DELETE FROM CREDIT_CARD WHERE CARD_NUMBER = :cardNumber")
    fun deleteCreditCardByCardNumber(cardNumber: String): Int

}