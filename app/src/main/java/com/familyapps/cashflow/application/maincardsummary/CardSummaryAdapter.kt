package com.familyapps.cashflow.application.maincardsummary

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.familyapps.cashflow.R


class CardSummaryAdapter(private val cardList: ArrayList<CardSummaryStatement>) :
    RecyclerView.Adapter<CardSummaryAdapter.CardSummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSummaryAdapter.CardSummaryViewHolder {
        val inflatedView = parent.inflate(R.layout.card_summary_activity, false)
        return CardSummaryViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: CardSummaryAdapter.CardSummaryViewHolder, position: Int) {
        val currentCard: CardSummaryStatement = cardList[position]

        holder.cardSummaryImageView.setImageResource(currentCard.cardImageResource)
        holder.cardNameTxtView.setText(currentCard.cardSummaryName)
        holder.cardSummaryTxtView.setText(currentCard.cardSummaryStatement)
    }

    class CardSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        var cardSummaryImageView: ImageView
        var cardNameTxtView: TextView
        var cardSummaryTxtView: TextView

        init {
            view.setOnClickListener(this)
            this.cardSummaryImageView = itemView.findViewById(R.id.cardSumaryImageView)
            this.cardNameTxtView = itemView.findViewById(R.id.summaryCardNameTextView)
            this.cardSummaryTxtView = itemView.findViewById(R.id.summaryCardStatementTextView)
        }

        companion object {
            private val CARD_KEY = "CARD"
        }

        override fun onClick(itemView: View) {
            val creditCardCredit = itemView.findViewById<TextView>(R.id.summaryCardStatementTextView).text
            val creditCardName = itemView.findViewById<TextView>(R.id.summaryCardNameTextView).text
            Log.i(
                "RecyclerView",
                String.format("Clicked on %s Credit Card with Credit %s", creditCardName, creditCardCredit)
            )
        }
    }
}