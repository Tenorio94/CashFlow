package com.familyapps.cashflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.familyapps.cashflow.application.maincardsummary.CardSummaryAdapter
import com.familyapps.cashflow.application.maincardsummary.CardSummaryStatement
import com.familyapps.cashflow.infraestructure.databaseextensions.populateBanks
import com.familyapps.cashflow.model.CashFlowDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var summaryRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var summaryRecyclerView: RecyclerView
    private lateinit var summaryRecViewAdapter: CardSummaryAdapter

    private var cardSummaryList: ArrayList<CardSummaryStatement> = arrayListOf()
    private var cashFlowDb: CashFlowDatabase? = CashFlowDatabase.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createCardList()
        createRecycleView()
        populateBanks(cashFlowDb)
    }

    private fun createCardList() {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_android,
                "$4,440.00",
                "American Express Platinum"
            )
        )
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_event_seat,
                "$4,780.00",
                cardName = "Banamex Oro"
            )
        )
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land,
                "$130.00",
                cardName = "Volaris 2.0"
            )
        )
    }

    private fun createRecycleView() {
        summaryRecyclerView = findViewById(R.id.cardSummaryRecyclerView)
        summaryRecyclerView.setHasFixedSize(true)

        summaryRecLayoutManager = LinearLayoutManager(this)
        summaryRecyclerView.layoutManager = summaryRecLayoutManager

        summaryRecViewAdapter = CardSummaryAdapter(cardSummaryList)
        summaryRecyclerView.adapter = summaryRecViewAdapter
    }

    fun addCard() {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land, "$6,780.00", "Banncomer Azul"
            )
        )
        Log.i("CreateRV", cardSummaryList[cardSummaryList.size - 1].cardSummaryName)
        summaryRecViewAdapter.notifyItemInserted(cardSummaryList.size - 1)

    }
}
