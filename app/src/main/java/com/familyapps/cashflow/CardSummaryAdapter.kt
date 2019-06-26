package com.familyapps.cashflow

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CardSummaryAdapter(private val cardListSummary: ArrayList<CardSummaryStatement>) :
    RecyclerView.Adapter<CardSummaryAdapter.CardSummaryViewHolder>() {

    class CardSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardSummaryImageView: ImageView
        var cardNameTxtView: TextView
        var cardSummaryTxtView: TextView

        init {
            this.cardSummaryImageView = itemView.findViewById(R.id.cardSumaryImageView)
            this.cardNameTxtView = itemView.findViewById(R.id.summaryCardNameTextView)
            this.cardSummaryTxtView = itemView.findViewById(R.id.summaryCardStatementTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSummaryViewHolder {
        val cardSummaryView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_summary_activity, parent, false)

        return CardSummaryViewHolder(cardSummaryView)
    }

    override fun getItemCount(): Int {
        return cardListSummary.size
    }

    override fun onBindViewHolder(holder: CardSummaryViewHolder, position: Int) {
        val currentCard : CardSummaryStatement = cardListSummary[position]

        holder.cardSummaryImageView.setImageResource(currentCard.cardImageResource)
        holder.cardNameTxtView.setText(currentCard.cardSummaryName)
        holder.cardSummaryTxtView.setText(currentCard.cardSummaryStatement)
    }
}