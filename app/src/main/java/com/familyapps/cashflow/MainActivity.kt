package com.familyapps.cashflow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.familyapps.cashflow.mainCardSummary.CardSummaryAdapter
import com.familyapps.cashflow.mainCardSummary.CardSummaryStatement


class MainActivity : AppCompatActivity() {

    private var cardSummaryList : ArrayList<CardSummaryStatement> = arrayListOf<CardSummaryStatement>()
    private var summaryRecViewAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createCardList()
        createRecycleView()
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
        val summaryRecyclerView: RecyclerView = findViewById(R.id.cardSummaryRecyclerView)
        val summaryRecLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        summaryRecViewAdapter = CardSummaryAdapter(cardSummaryList)

        summaryRecyclerView.setHasFixedSize(true)
        summaryRecyclerView.layoutManager = summaryRecLayoutManager
        summaryRecyclerView.adapter = summaryRecViewAdapter
    }

    fun addCard(view: View) {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land,
                "$6,780.00",
                "Banncomer Azul"
            )
        )
        summaryRecViewAdapter?.notifyItemInserted(cardSummaryList.size - 1)

//        val parentLayout = findViewById<LinearLayout>(R.id.cardLinearLayout)
//        val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//        val viewAdded = inflater.inflate(R.layout.card_summary_activity, parentLayout,false)
//        val newToAdd = viewAdded.findViewById<LinearLayout>(R.id.cardHrzLinearLayout)
//
//        val stringID = newToAdd.id as String
//        val currentSequence = newToAdd.findViewById<>()
//
//        parentLayout.addView(viewAdded)
    }
}
