package com.familyapps.cashflow.application.carddetails

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository

class CardDetailActivity : AppCompatActivity() {

    private var cashFlowDb : CashFlowDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val uiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_details_activity)

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        cashFlowDb = CashFlowDatabase.getInstance(this) as CashFlowDatabase?



//        val userToInsert = User(0, "Gerardo", "Tenorio", null, "geteca94@gmail.com")
//
//        val task = Runnable { cashFlowDb?.userRepository()?.insertUser(userToInsert) }
//        dbWorkerThread.postTask(task)
//
//        val fetchTask = cashFlowDb?.userRepository()?.findUserByEmail("geteca94@gmail.com") as User
//        Log.i("DBTest", String.format("%s found for email %s", fetchTask.firstName, fetchTask.email))
//
        val cardDetails = intent.getParcelableExtra<CardDetailDTO>("extra")
        println(cardDetails.cardName)

        findViewById<TextView>(R.id.cardNameDetailsTextView).text = cardDetails.cardName


    }
}