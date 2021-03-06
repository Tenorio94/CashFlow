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

    val logTag = "CF_CD_TxnPopulation"
    Log.i(logTag, "Running transaction population...")

    val txnSummaryList = arrayListOf<CardDetailsTransactionSummary>()


    GlobalScope.launch {
        val txnRepository = cashFlowDb?.transactionRepository()
        Log.i(logTag, String.format("Looking for transactions for card %s", cardNumber))
        val txnList = txnRepository?.findTransactionsByCardNumber(cardNumber)
        Thread.sleep(15)

        if (txnList!!.isNotEmpty()) {
            txnList.forEach {
                Log.i(
                    logTag,
                    String.format("Transaction found for Card %s with Description %s", cardNumber, it.description)
                )
                val cardDetailsTxn = CardDetailsTransactionSummary(
                    convertDoubleToCash(it.amountPerMonth!!),
                    convertInstantToString(it.txnDate),
                    it.description!!,
                    it.id.toString()
                )
                txnSummaryList.add(cardDetailsTxn)
            }
        } else {
            Log.i(logTag, "No transactions found... Populating")
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

    val logTag = "CF_MA_StatementPopulating"
    Log.i(logTag, "Running statement population...")

    GlobalScope.launch {
        val statementRepository = cashFlowDb?.statementRepository()
        val cardRepository = cashFlowDb?.cardRepository()
        val statementList = statementRepository?.findStatementsByMonthAndEmail(currentMonth, currentEmail)
        var cardSearch: CreditCard


        if (statementList!!.isNotEmpty()) {
            statementList.forEach {
                Log.i(logTag, String.format("Statement for Card Number %s found... Looking for card", it.cardNumber))
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
    val txn1Date = Instant.now().plusSeconds(Random.nextLong(86400))
    val txn1OrigDate = txn1Date.minusSeconds(10512000)

    val txn1 = Transaction(
        null,
        "Macbook Pro",
        "MacStore",
        29850.00,
        true,
        "24",
        29850.00 / 24,
        txn1Date,
        "5499490538837865",
        "79260547",
        txn1OrigDate
    )

    val txn2Date = Instant.now().plusSeconds(Random.nextLong(86400))
    val txn2OrigDate = txn2Date.minusSeconds(13140000)

    val txn2 = Transaction(
        null,
        "Recámara Principal",
        "Mueblería Plascencia",
        12500.00,
        true,
        "6",
        12500.00 / 6,
        txn2Date,
        "371775435543443",
        "tenorio94",
        txn2OrigDate
    )

    val txn3Date = Instant.now().plusSeconds(Random.nextLong(86400))
    val txn3OrigDate = txn3Date.minusSeconds(23652000)

    val txn3 = Transaction(
        null,
        "Conchón Spring Air",
        "Super Colchones",
        24500.00,
        true,
        "12",
        24500.00 / 12,
        txn3Date,
        "5499490538837865",
        "79260547",
        txn3OrigDate
    )

    val txn4Date = Instant.now().plusSeconds(Random.nextLong(86400))
    val txn4OrigDate = txn4Date.minusSeconds(10512000)

    val txn4 = Transaction(
        null,
        "Vuelo Ramonchis",
        "Volaris",
        7690.00,
        false,
        "1",
        24500.00 / 1,
        txn4Date,
        "4196910100287234",
        "gtenoriocastillo@gmail.com",
        txn4OrigDate
    )

    val txn5Date = Instant.now().plusSeconds(Random.nextLong(86400))
    val txn5OrigDate = txn5Date.minusSeconds(2628000)

    val txn5 = Transaction(
        null,
        "Carga Gas",
        "Gas Rosa",
        2001.64,
        false,
        "1",
        2001.64 / 1,
        txn5Date,
        "5288439112015634",
        "79260547",
        txn5OrigDate
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
        "371775435543443",
        "tenorio94",
        "gtenoriocastillo@gmail.com"
    )

    val banamexOroStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "5499490538837865",
        "79260547",
        "gtenoriocastillo@gmail.com"
    )

    val banamexClasicaStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "5288439112015634",
        "79260547",
        "gtenoriocastillo@gmail.com"
    )

    val invexStatement = Statement(
        null,
        currentMonth,
        Random.nextDouble(15000.00),
        Instant.now(),
        Instant.now().plusSeconds(2419200),
        "4196910100287234",
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
        "371775435543443",
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
        "5499490538837865",
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
        "5288439112015634",
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
        "4196910100287234",
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

    val logTag = "CF_MA_CardPopulating"
    Log.i(logTag, "Running card population...")

    GlobalScope.launch {
        cardList = cardRepository?.findAllCreditCardsByEmail(MainActivity.email)!!

        if (cardList.isNotEmpty()) {
            Log.i(logTag, "Cards have already been populated...")
            cardList.forEach {
                Log.i(logTag, String.format("%s card found with number %s", it.cardName, it.cardNumber))
            }
        } else {
            cardRepository?.insertBatchCreditCard(createCardList())
            Log.i(logTag, "Cards empty... DB Updated")
        }
    }
}

fun populateBanks(cashFlowDb: CashFlowDatabase?) {
    val logTag = "CF_MA_BankPopulating"
    Log.i(logTag, "Running bank population...")

    GlobalScope.launch {
        val bankRepository = cashFlowDb?.bankRepository()
        var bankList = cashFlowDb?.bankRepository()?.findAllBanks()

        if (bankList!!.isNotEmpty()) {
            Log.i(logTag, "Banks have been populated before.")
            bankList.forEach {
                Log.i(logTag, String.format("%s in the list with id %d", it.bankName, it.id))
            }
        } else {
            bankRepository?.insertBankBatch(createBankList())
            Log.i(logTag, "No Banks present... Banks have been inserted.")
        }
    }
}