package com.familyapps.cashflow.model.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountRepository {

    @Insert
    fun insertNewAccount(account : Account)

    @Update
    fun updateAccount(account : Account)

    @Query("DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER = :accountNumber")
    fun deleteAccount(accountNumber : String) : Long

    @Query("SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = :accountNumber")
    fun findAccountByAccountNumber(accountNumber : String) : Account

    @Query("SELECT * FROM ACCOUNT WHERE ACCOUNT_EMAIL = :accountEmail")
    fun findAccountByAccountEmail(accountEmail : String) : Account

    @Query("SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = :accountNumber AND ACCOUNT_EMAIL = :accountEmail")
    fun findByAccountAndAccountEmail(accountNumber : String, accountEmail : String) : Account

    @Query("SELECT * FROM ACCOUNT")
    fun findAllAccounts() : List<Account>

}