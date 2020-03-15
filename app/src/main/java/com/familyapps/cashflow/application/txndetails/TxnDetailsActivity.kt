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

class TxnDetailsActivity : AppCompatActivity() {
    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var txnRepository: TransactionRepository
    private lateinit var dbWorkerThread: DbWorkerThread

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
    private fun retrieveTransactionDetails(txnDetail: Int) : Transaction? {
        var transaction : Transaction? = null
        GlobalScope.launch {
            transaction = txnRepository.findTransactionByTxnId(txnDetail)

            if (transaction == null) {
                Log.d("CF_TXN_RetrieveTransaction", String.format("Error retrieving transaction %s, please verify Database", txnDetail))
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

        storeNameTxnTextView.text = myTransaction.store
        monthlyAmountTxnTextView.text = convertDoubleToCash(myTransaction.amount)
        txnDateTxnDetailTextView.text = convertInstantToString(myTransaction.txnDate)
        txnDescriptionTextView.text = myTransaction.description
    }
}