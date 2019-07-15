package com.familyapps.cashflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import com.familyapps.cashflow.application.maincardsummary.CardSummaryAdapter
import com.familyapps.cashflow.application.maincardsummary.CardSummaryStatement
import com.familyapps.cashflow.infraestructure.databaseextensions.*
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var summaryRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var summaryRecyclerView: RecyclerView
    private lateinit var summaryRecViewAdapter: CardSummaryAdapter

    private var cardSummaryList: ArrayList<CardSummaryStatement> = arrayListOf()

    companion object {
        var currentMonth = LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.US)
        var email = "gtenoriocastillo@gmail.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val LOG_TAG = "CF_MA_MainCreate"
        val cashFlowDb: CashFlowDatabase? = CashFlowDatabase.getInstance(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, String.format("Id of resource: %s",R.drawable.ic_android.toString()))
        populateBanks(cashFlowDb)
        populateCards(cashFlowDb)
        cardSummaryList = populateStatements(cashFlowDb)
        createRecycleView()

        cardSummaryList.forEach {
            Log.i(LOG_TAG, String.format("%s statement for %s card.", it.cardSummaryStatement, it.cardSummaryName))
        }
    }

    private fun createRecycleView() {
        summaryRecyclerView = findViewById(R.id.cardSummaryRecyclerView)
        summaryRecyclerView.setHasFixedSize(true)

        summaryRecLayoutManager = LinearLayoutManager(this)
        summaryRecyclerView.layoutManager = summaryRecLayoutManager

        summaryRecViewAdapter = CardSummaryAdapter(cardSummaryList)
        summaryRecyclerView.adapter = summaryRecViewAdapter
    }

    fun addCard(view: View) {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land, "$6,780.00", "Banncomer Azul", "4"
            )
        )
        Log.i("CreateRV", cardSummaryList[cardSummaryList.size - 1].cardSummaryName)
        summaryRecViewAdapter.notifyItemInserted(cardSummaryList.size - 1)

    }
}
