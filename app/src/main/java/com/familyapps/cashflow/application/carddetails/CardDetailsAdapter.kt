package com.familyapps.cashflow.application.carddetails

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.maincardsummary.inflate

class CardDetailsAdapter (private val statementList: ArrayList<CardDetailsTransactionSummary>) :
    RecyclerView.Adapter<CardDetailsAdapter.CardDetailsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailsViewHolder {
        val inflatedView = parent.inflate(R.layout.card_summary_statement_activity, false)
        return CardDetailsViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = statementList.size

    override fun onBindViewHolder(holder: CardDetailsViewHolder, position: Int) {
        val currentTxn: CardDetailsTransactionSummary = statementList[position]

        holder.cardMonthlyTxnDate.setText(currentTxn.txnDate.toString())
        holder.cardMonthlyAmountTxtView.setText(currentTxn.txnMonthAmount.toString())
        holder.cardTxnDescription.setText(currentTxn.txnDescription)
    }

    class CardDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        var cardMonthlyAmountTxtView: TextView
        var cardMonthlyTxnDate: TextView
        var cardTxnDescription: TextView

        init {
            view.setOnClickListener(this)
            this.cardTxnDescription = itemView.findViewById(R.id.cardSumaryBuying)
            this.cardMonthlyAmountTxtView = itemView.findViewById(R.id.txnAmount)
            this.cardMonthlyTxnDate = itemView.findViewById(R.id.summaryCardTransactionDate)
        }

        override fun onClick(itemView: View) {
            Log.i("Card Details RecyclerView", "Recycler Clicked...")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}