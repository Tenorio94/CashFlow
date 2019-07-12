package com.familyapps.cashflow.model.bank

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BankRepository {

    @Insert
    fun insertBank(bank: Bank) : Long

    @Update
    fun updateBank(bank: Bank)

    @Query("DELETE FROM BANK")
    fun deleteAllBanks() : List<Long>

    @Query("DELETE FROM BANK WHERE BANK_NAME = :bankName")
    fun deleteBank(bankName : String) : Long

    @Query(value = "SELECT * FROM BANK WHERE BANK_NAME = :bankName")
    fun findBankByBankName(bankName : String) : Bank

    @Query(value = "SELECT * FROM BANK")
    fun findAllBanks() : List<Bank>
}