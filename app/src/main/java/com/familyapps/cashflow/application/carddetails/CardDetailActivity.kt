package com.familyapps.cashflow.application.carddetails

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.infraestructure.databaseextensions.mapCardWithImage
import com.familyapps.cashflow.infraestructure.databaseextensions.populateTransactions
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.card.CreditCard
import com.familyapps.cashflow.model.transaction.Transaction
import com.familyapps.cashflow.model.user.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CardDetailActivity : AppCompatActivity() {

    private var cashFlowDb: CashFlowDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val uiHandler = Handler()

    private lateinit var detailsRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var detailsRecViewAdapter: CardDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_details_activity)
        cashFlowDb = CashFlowDatabase.getInstance(this)

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        GlobalScope.launch {
            populateDatabase()
        }


        Log.i("AfterDB", "Data inserted and following...")
        val cardDetails = intent.getParcelableExtra<CardDetailDTO>("extra")
        println(cardDetails.cardName)

        val txnList = populateTransactions(cashFlowDb, cardDetails.cardNumber)
        findViewById<ImageView>(R.id.cardDetailsImageView).setImageDrawable(resources.getDrawable(assignCardImage(cashFlowDb!!, cardDetails.cardNumber)))
        findViewById<TextView>(R.id.cardNameDetailsTextView).text = cardDetails.cardName

        createRecyclerView(txnList)

    }

    fun createRecyclerView(txnList: ArrayList<CardDetailsTransactionSummary>) {
        detailsRecyclerView = findViewById(R.id.cardDetailsStatement)
        detailsRecyclerView.setHasFixedSize(true)

        detailsRecLayoutManager = LinearLayoutManager(this)
        detailsRecyclerView.layoutManager = detailsRecLayoutManager

        detailsRecViewAdapter = CardDetailsAdapter(txnList)
        detailsRecyclerView.adapter = detailsRecViewAdapter
    }

    fun populateDatabase() {
        val userRepository = cashFlowDb?.userRepository()
        val userExists = userRepository?.userExistsByEmail("geteca94@gmail.com")

        if (!userExists!!) {
            val userToInsert = User(0, "Gerardo", "Tenorio", null, "geteca94@gmail.com")
            val userId = userRepository?.insertUser(userToInsert)
            Log.i("DBTest", String.format("%s has been inserted with id %d", userToInsert.email, userId))
        } else {
            Log.i("DBTest", "User registered...")
        }
    }

    fun assignCardImage(cashFlowDb: CashFlowDatabase, cardNumber: String) : Int{
        val creditCardRepository = cashFlowDb.cardRepository()
        var cardData: CreditCard? = null
        GlobalScope.launch {
            cardData = creditCardRepository.findCreditCardByCardNumber(cardNumber)
        }
        Thread.sleep(15)
        println(mapCardWithImage(cardData!!.bankCardName))
        return mapCardWithImage(cardData!!.bankCardName)
    }
}