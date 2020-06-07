package com.familyapps.cashflow.application.txndetails

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.familyapps.cashflow.R
import com.familyapps.cashflow.infraestructure.convertDoubleToCash
import com.familyapps.cashflow.infraestructure.convertInstantToString
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.transaction.Transaction
import com.familyapps.cashflow.model.transaction.TransactionRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.*

class TxnDetailsActivity : AppCompatActivity() {
    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var txnRepository: TransactionRepository
    private lateinit var dbWorkerThread: DbWorkerThread

    private val LOGTAG = "CF_TXN_RetrieveTransaction";

    override fun onCreate(savedInstanceState: Bundle?) {
        cashFlowDb = CashFlowDatabase.getInstance(this)
        txnRepository = cashFlowDb.transactionRepository()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_details_activity)

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        val txnDetails = intent.getParcelableExtra<TxnDetailDto>("extra")
        val myTransaction = retrieveTransactionDetails(txnDetails.transactionID)
        populateTransactionViewFields(myTransaction!!)
    }

    /**
     * Function used to retrieve transaction details from the database. Query parameters consist
     * in the Transaction Id itself.
     *
     * @param txnDetail - Int containing TransactionID from Card Details Activity RecycleViewer
     *
     * @return Transaction - Retrieved transaction from the database if any.
     */
    private fun retrieveTransactionDetails(txnDetail: Int): Transaction? {
        var transaction: Transaction? = null
        Log.d(LOGTAG, String.format("Retrieving transaction %s from database", txnDetail))
        GlobalScope.launch {
            transaction = txnRepository.findTransactionByTxnId(txnDetail)

            if (transaction == null) {
                Log.d(
                    LOGTAG,
                    String.format(
                        "Error retrieving transaction %s, please verify Database",
                        txnDetail
                    )
                )
            }
        }
        Thread.sleep(15)
        return transaction
    }

    /**
     * Function used to alter and populate the fields within @{transaction_details_activity.xml}
     * layout file, this uses the previously fetched transaction from the database to populate this
     * view.
     *
     * @param myTransaction - Transaction fetched from the database with Transaction ID.
     */
    private fun populateTransactionViewFields(myTransaction: Transaction) {
        val storeNameTxnTextView = findViewById<TextView>(R.id.storeNameTxnDetails)
        val monthlyAmountTxnTextView = findViewById<TextView>(R.id.monthlyAmountTxnDetails)
        val txnDateTxnDetailTextView = findViewById<TextView>(R.id.txnDateTxnDetails)
        val txnDescriptionTextView = findViewById<TextView>(R.id.txnDescriptionTxnDetails)
        val txnDescriptionPendingAmount = findViewById<TextView>(R.id.pendingPayTxnDetails)
        val txnDescriptionPendingPayments = findViewById<TextView>(R.id.pendingPaymentsTxnDetails)

        Log.d(LOGTAG, String.format("Store Name is: %s", storeNameTxnTextView.text))
        Log.d(LOGTAG, String.format("Monthly Amount is: %s", convertDoubleToCash(myTransaction.amount)))
        Log.d(LOGTAG, String.format("Transaction Date is: %s", convertInstantToString(myTransaction.txnDate)))
        Log.d(LOGTAG, String.format("Transaction Description is: %s", myTransaction.description))
        Log.d(LOGTAG, String.format("Pending Amount is: %s", convertDoubleToCash(getPendingAmount(myTransaction))))
        Log.d(LOGTAG, String.format("Time Pending is: %s", getPendingMonths(myTransaction)))

        storeNameTxnTextView.text = myTransaction.store
        monthlyAmountTxnTextView.text = convertDoubleToCash(myTransaction.amount)
        txnDateTxnDetailTextView.text = convertInstantToString(myTransaction.txnDate)
        txnDescriptionTextView.text = myTransaction.description
        txnDescriptionPendingAmount.text = convertDoubleToCash(getPendingAmount(myTransaction))
        txnDescriptionPendingPayments.text = getPendingMonths(myTransaction)
    }

    /**
     * Function used to get the pendingAmount to pay via calculation by subtracting the current month
     * minus the original date and dividing the amount currently payed with the months payed.
     *
     * @param myTransaction - Transaction fetched from the database
     *
     * @return Double containing the pending amount to pay.
     */
    private fun getPendingAmount(myTransaction: Transaction): Double {

        if (myTransaction.differedTime == "1") {
            return myTransaction.amount
        }

        val originalTransactionDate =
            LocalDateTime.ofInstant(myTransaction.originalTxnDate, ZoneOffset.UTC).toLocalDate()
        val transactionDate =
            LocalDateTime.ofInstant(myTransaction.txnDate, ZoneOffset.UTC).toLocalDate()
        val amount = myTransaction.amount
        val monthsPaid = Period.between(originalTransactionDate, transactionDate).months

        return amount / monthsPaid
    }

    /**
     * Function used to calculate the pending months left to pay by substracting the original date
     * minus the current date as of today. The returned value is the String of that operation.
     *
     * @param myTransaction - Transaction fetched from the database.
     *
     * @return String containing the current payments as in format of x/x
     */
    private fun getPendingMonths(myTransaction: Transaction): String {

        if (myTransaction.differedTime == "1") {
            return "1/1"
        }

        val transactionDate =
            LocalDateTime.ofInstant(myTransaction.txnDate, ZoneOffset.UTC).toLocalDate()
        val originalTransactionDate =
            LocalDateTime.ofInstant(myTransaction.originalTxnDate, ZoneOffset.UTC).toLocalDate()
        val totalDiffered = myTransaction.differedTime
        val monthsPaidConversion = Period.between(originalTransactionDate, transactionDate).toString()
        val monthsPaid = monthsPaidConversion.substring(monthsPaidConversion.indexOf('P') + 1, monthsPaidConversion.indexOf('M'))

        return String.format("%s/%s", monthsPaid, totalDiffered)
    }
}