package com.familyapps.cashflow.infraestructure.databaseextensions

import android.util.Log
import com.familyapps.cashflow.MainActivity
import com.familyapps.cashflow.MainActivity.Companion.currentMonth
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.carddetails.CardDetailsTransactionSummary
import com.familyapps.cashflow.application.maincardsummary.CardSummaryStatement
import com.familyapps.cashflow.infraestructure.convertDoubleToCash
import com.familyapps.cashflow.infraestructure.convertInstantToString
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.bank.Bank
import com.familyapps.cashflow.model.card.CreditCard
import com.familyapps.cashflow.model.statement.Statement
import com.familyapps.cashflow.model.transaction.Transaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.collections.ArrayList
import kotlin.random.Random

private lateinit var workerThread: DbWorkerThread


fun populateTransactions(cashFlowDb: CashFlowDatabase?, cardNumber: String): ArrayList<CardDetailsTransactionSummary> {
    workerThread = DbWorkerThread("cashFlowThread")
    workerThread.start()

    val LOG_TAG = "CF_CD_TxnPopulation"
    Log.i(LOG_TAG, "Running transaction population...")

    val txnSummaryList = arrayListOf<CardDetailsTransactionSummary>()


    GlobalScope.launch {
        val txnRepository = cashFlowDb?.transactionRepository()
        Log.i(LOG_TAG, String.format("Looking for transactions for card %s", cardNumber))
        val txnList = txnRepository?.findAllTransaction()

        if (txnList!!.isNotEmpty()) {
            txnList.forEach {
                Log.i(
                    LOG_TAG,
                    String.format("Transaction found for Card %s with Description %s", cardNumber, it.description)
                )
                val cardDetailsTxn = CardDetailsTransactionSummary(
                    convertDoubleToCash(it.amountPerMonth!!),
                    convertInstantToString(it.txnDate),
                    it.description!!
                )
                txnSummaryList.add(cardDetailsTxn)
            }
        } else {
            Log.i(LOG_TAG, "No transactions found... Populating")
            txnRepository.insertBatchTransaction(createTxnList())
        }
    }
    Thread.sleep(50)
    return txnSummaryList
}

fun populateStatements(cashFlowDb: CashFlowDatabase?): ArrayList<CardSummaryStatement> {
    workerThread = DbWorkerThread("cashFlowThread")
    workerThread.start()

    val currentEmail = MainActivity.email
    val cardStatementList = arrayListOf<CardSummaryStatement>()

    val LOG_TAG = "CF_MA_StatementPopulating"
    Log.i(LOG_TAG, "Running statement population...")

    GlobalScope.launch {
        val statementRepository = cashFlowDb?.statementRepository()
        val cardRepository = cashFlowDb?.cardRepository()
        val statementList = statementRepository?.findStatementsByMonthAndEmail(currentMonth, currentEmail)
        var cardSearch: CreditCard


        if (statementList!!.isNotEmpty()) {
            statementList.forEach {
                Log.i(LOG_TAG, String.format("Statement for Card Number %s found... Looking for card", it.cardNumber))
                cardSearch = cardRepository?.findCreditCardByCardNumber(it.cardNumber)!!

                val cardStatement =
                    CardSummaryStatement(
                        mapCardWithImage(cardSearch.bankCardName),
                        convertDoubleToCash(it.statementAmount),
                        cardSearch.cardName,
                        it.cardNumber
                    )
                cardStatementList.add(cardStatement)
            }
        } else {
            statementRepository.insertBatchStatement(createStatementList())
        }
    }

    Thread.sleep(50)
    return cardStatementList
}


fun mapCardWithImage(cardName: String): Int {

    return when (cardName) {
        "AMEX_PT" -> R.drawable.amex_platinum_card
        "BMX_CLASICA" -> R.drawable.banamex_clasica
        "BMX_ORO" -> R.drawable.banamex_oro
        "INV_VOLARIS2" -> R.drawable.invex_volaris2
        else -> R.drawable.ic_android
    }
}

fun createTxnList(): List<Transaction> {
    val txn1 = Transaction(
        null,
        "Macbook Pro",
        "MacStore",
        29850.00,
        true,
        "24",
        29850.00 / 24,
        Instant.now().plusSeconds(Random.nextLong(86400)),
        "5499490538837717",
        "79260547"
    )

    val txn2 = Transaction(
        null,
        "Recámara Principal",
        "Mueblería Plascencia",
        12500.00,
        true,
        "6",
        12500.00 / 6,
        Instant.now().plusSeconds(Random.nextLong(86400)),
        "371775436701005",
        "tenorio94"
    )

    val txn3 = Transaction(
        null,
        "Conchón Spring Air",
        "Super Colchones",
        24500.00,
        true,
        "12",
        24500.00 / 12,
        Instant.now().plusSeconds(Random.nextLong(86400)),
        "5499490538837717",
        "79260547"
    )

    val txn4 = Transaction(
        null,
        "Vuelo Ramonchis",
        "Volaris",
        7690.00,
        false,
        "1",
        24500.00 / 1,
        Instant.now().plusSeconds(Random.nextLong(86400)),
        "4196910100284245",
        "gtenoriocastillo@gmail.com"
    )

    val txn5 = Transaction(
        null,
        "Carga Gas",
        "Gas Rosa",
        2001.64,
        false,
        "1",
        2001.64 / 1,
        Instant.now().plusSeconds(Random.nextLong(86400)),
        "5288439112077716",
        "79260547"
    )


    return listOf(txn1, txn2, txn3, txn4, txn5)
}

fun createStatementList(): List<Statement> {
    val amexStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "371775436701005",
        "tenorio94",
        "gtenoriocastillo@gmail.com"
    )

    val banamexOroStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "5499490538837717",
        "79260547",
        "gtenoriocastillo@gmail.com"
    )

    val banamexClasicaStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "5288439112077716",
        "79260547",
        "gtenoriocastillo@gmail.com"
    )

    val invexStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "4196910100284245",
        "gtenoriocastillo@gmail.com",
        "gtenoriocastillo@gmail.com"
    )

    return listOf(amexStatement, banamexOroStatement, banamexClasicaStatement, invexStatement)
}

fun createCardList(): List<CreditCard> {
    val amexCard = CreditCard(
        null,
        "American Express Platinum",
        "AMEX_PT",
        "371775436701005",
        Instant.now().plusSeconds(155520000),
        Instant.now(),
        "CREDIT",
        "American Express",
        "tenorio94",
        "gtenoriocastillo@gmail.com",
        Instant.now().plusNanos(216000),
        120000.00,
        Random.nextDouble(120000.00),
        3000.00,
        330.00
    )
    val banamexOro = CreditCard(
        null,
        "Banamex Oro",
        "BMX_ORO",
        "5499490538837717",
        Instant.now().plusSeconds(155520000),
        Instant.now(),
        "CREDIT",
        "Banamex",
        "79260547",
        "gtenoriocastillo@gmail.com",
        Instant.now().plusNanos(216000),
        111000.00,
        Random.nextDouble(111000.00),
        980.00,
        110.00
    )
    val banamexClasica = CreditCard(
        null,
        "Banamex Clasica",
        "BMX_CLASICA",
        "5288439112077716",
        Instant.now().plusSeconds(155520000),
        Instant.now(),
        "CREDIT",
        "Banamex",
        "79260547",
        "gtenoriocastillo@gmail.com",
        Instant.now().plusNanos(216000),
        19000.00,
        Random.nextDouble(19000.00),
        680.00,
        80.00
    )
    val invexVolaris = CreditCard(
        null,
        "INVEX Volaris 2.0",
        "INV_VOLARIS2",
        "4196910100284245",
        Instant.now().plusSeconds(155520000),
        Instant.now(),
        "CREDIT",
        "Invex",
        "gtenoriocastillo@gmail.com",
        "gtenoriocastillo@gmail.com",
        Instant.now().plusNanos(216000),
        35000.00,
        Random.nextDouble(35000.00),
        3600.00,
        380.00
    )
    return listOf(amexCard, banamexClasica, banamexOro, invexVolaris)
}

fun createBankList(): ArrayList<Bank> {
    val bankList = arrayListOf<Bank>()
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
    return bankList
}

fun populateCards(cashFlowDb: CashFlowDatabase?) {
    val cardRepository = cashFlowDb?.cardRepository()
    var cardList: List<CreditCard> = listOf()

    val LOG_TAG = "CF_MA_CardPopulating"
    Log.i(LOG_TAG, "Running card population...")

    GlobalScope.launch {
        cardList = cardRepository?.findAllCreditCardsByEmail(MainActivity.email)!!

        if (cardList.isNotEmpty()) {
            Log.i(LOG_TAG, "Cards have already been populated...")
            cardList.forEach {
                Log.i(LOG_TAG, String.format("%s card found with number %s", it.cardName, it.cardNumber))
            }
        } else {
            cardRepository?.insertBatchCreditCard(createCardList())
            Log.i(LOG_TAG, "Cards empty... DB Updated")
        }
    }
}

fun populateBanks(cashFlowDb: CashFlowDatabase?) {
    val LOG_TAG = "CF_MA_BankPopulating"
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
            bankRepository?.insertBankBatch(createBankList())
            Log.i(LOG_TAG, "No Banks present... Banks have been inserted.")
        }
    }
}