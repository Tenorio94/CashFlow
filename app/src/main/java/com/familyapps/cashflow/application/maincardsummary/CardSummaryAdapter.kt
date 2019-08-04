package com.familyapps.cashflow.application.maincardsummary

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.carddetails.CardDetailActivity
import com.familyapps.cashflow.application.carddetails.CardDetailDTO
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class CardSummaryAdapter(private val cardList: ArrayList<CardSummaryStatement>) :
    RecyclerView.Adapter<CardSummaryAdapter.CardSummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSummaryViewHolder {
        val inflatedView = parent.inflate(R.layout.card_summary_activity, false)
        return CardSummaryViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: CardSummaryViewHolder, position: Int) {
        val currentCard: CardSummaryStatement = cardList[position]

        holder.cardSummaryImageView.setImageResource(currentCard.cardImageResource)
        holder.cardNameTxtView.setText(currentCard.cardSummaryName)
        holder.cardSummaryTxtView.setText(currentCard.cardSummaryStatement)
        holder.cardSummaryCardNumber.setText(currentCard.cardSummaryNumber)
    }

    class CardSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        var cardSummaryImageView: ImageView
        var cardNameTxtView: TextView
        var cardSummaryTxtView: TextView
        var cardSummaryCardNumber: TextView

        init {
            view.setOnClickListener(this)
            this.cardSummaryImageView = itemView.findViewById(R.id.cardSumaryImageView)
            this.cardNameTxtView = itemView.findViewById(R.id.summaryCardNameTextView)
            this.cardSummaryTxtView = itemView.findViewById(R.id.summaryCardStatementTextView)
            this.cardSummaryCardNumber = itemView.findViewById(R.id.cardSummaryCardNumber)
        }

        companion object {
            private val CARD_KEY = "CARD"
        }

        override fun onClick(itemView: View) {
            val context = itemView.context

            val creditCardCredit = itemView.findViewById<TextView>(R.id.summaryCardStatementTextView).text.toString()
            val creditCardName = itemView.findViewById<TextView>(R.id.summaryCardNameTextView).text.toString()
            val creditCardNumber = itemView.findViewById<TextView>(R.id.cardSummaryCardNumber).text.toString()
            val creditCardImage = itemView.findViewById<ImageView>(R.id.cardSumaryImageView).drawable

            val detailsDTO = CardDetailDTO(creditCardName, creditCardCredit, 1, creditCardNumber)

            Log.i("CardSummaryOnClick", String.format("%s Card with Statement %s sent", creditCardName, creditCardCredit))

            val cardDetailsIntent = Intent(context, CardDetailActivity::class.java).apply {
                putExtra("extra", detailsDTO)
            }

            context.startActivity(cardDetailsIntent)
        }
    }
}