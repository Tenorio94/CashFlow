package com.familyapps.cashflow.application.carddetails

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R

class CardDetailsAdapter (private val statementList: ArrayList<CardDetailsTransactionSummary>) :
    RecyclerView.Adapter<CardDetailsAdapter.CardDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailsViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = statementList.size

    override fun onBindViewHolder(holder: CardDetailsViewHolder, position: Int) {
        val currentTxn: CardDetailsTransactionSummary = statementList[position]

        holder.
        holder.cardSummaryImageView.setImageResource(currentCard.cardImageResource)
        holder.cardNameTxtView.setText(currentCard.cardSummaryName)
        holder.cardSummaryTxtView.setText(currentCard.cardSummaryStatement) //To change body of created functions use File | Settings | File Templates.
    }

    class CardDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        var cardMonthlyAmountTxtView: TextView
        var cardMonthlyTxnDate: TextView
        var cardTxnDescription: TextView

        init {
            view.setOnClickListener(this)
            this.cardTxnDescription = itemView.findViewById(R.id.cardSumaryBuying)
            this.cardMonthlyAmountTxtView = itemView.findViewById(R.id.cardSumaryImageView)
            this.cardMonthlyTxnDate = itemView.findViewById(R.id.txnAmount)
        }

        override fun onClick(p0: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}