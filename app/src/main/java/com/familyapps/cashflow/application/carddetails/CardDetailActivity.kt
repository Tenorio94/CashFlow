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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CardDetailActivity : AppCompatActivity() {

    private var cashFlowDb: CashFlowDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val uiHandler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_details_activity)
        cashFlowDb = CashFlowDatabase.getInstance(this)

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        GlobalScope.launch {
            populateDatabase()
        }


//        var data: List<UserDC>?

//        GlobalScope.launch {
//            cashFlowDb?.runInTransaction {
//
//                cashFlowDb?.userDCRepository()?.insertUser(userToInsert)
//                Log.i("DBTest", String.format("%s has been inserted", userToInsert.email))
//            }
//        }
//
//        GlobalScope.launch {
//            cashFlowDb?.runInTransaction {
//
//                data = cashFlowDb?.userDCRepository()?.getAllUserDCs()
//                Log.i("DBTest", "Fetching Database...")
//
//                data?.forEach {
//                    Log.i("DBTest", String.format("%s found for email %s", it.firstName, it.email))
//                }
//            }
//        }

//        val fetchTask = Runnable { cashFlowDb?.userDCRepository()?.getAllUserDCs() }
//        dbWorkerThread.postTask(fetchTask)
//        Log.i("DBTest", String.format("%s retrieved", fetchTask))
//        Log.i("DBTest", String.format("%s found for email %s", fetchTask.firstName, fetchTask.email))

        Log.i("AfterDB", "Data inserted and following...")
        val cardDetails = intent.getParcelableExtra<CardDetailDTO>("extra")
        println(cardDetails.cardName)

        findViewById<TextView>(R.id.cardNameDetailsTextView).text = cardDetails.cardName
    }

    fun populateDatabase() {
        val userRepository = cashFlowDb?.userRepository()
        val userExists = userRepository?.userExistsByEmail("geteca94@gmail.com")

        if(!userExists!!) {
            val userToInsert = User(0, "Gerardo", "Tenorio", null, "geteca94@gmail.com")
            val userId = userRepository?.insertUser(userToInsert)
            Log.i("DBTest", String.format("%s has been inserted with id %d", userToInsert.email, userId))
        }
        else {
            Log.i("DBTest", "User registered...")
        }
    }
}