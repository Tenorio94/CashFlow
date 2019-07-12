package com.familyapps.cashflow.infraestructure.databaseextensions

import android.util.Log
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.bank.Bank
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



fun populateBanks(cashFlowDb : CashFlowDatabase?) {
    val LOG_TAG = "MA_BankPopulating"
    Log.i(LOG_TAG, "Running bank population...")

    GlobalScope.launch {
        val bankRepository = cashFlowDb?.bankRepository()
        var bankList = cashFlowDb?.bankRepository()?.findAllBanks()

        if (bankList!!.isNotEmpty()) {
            Log.i(LOG_TAG, "Banks have been populated before.")
            bankList.forEach {
                Log.i(LOG_TAG, String.format("%s in the list with id %d", it.bankName, it.id))
            }
        } else {
            bankList = arrayListOf<Bank>()
            val banks = listOf<String>(
                "American Express",
                "BBVA",
                "Banamex",
                "Invex",
                "Inbursa",
                "Santander",
                "Scotiabank"
            )
            banks.forEach {
                bankList.add(Bank(null, it))
            }
            bankRepository?.insertBankBatch(bankList)
            Log.i(LOG_TAG, "No Banks present... Banks have been inserted.")
        }
    }
}