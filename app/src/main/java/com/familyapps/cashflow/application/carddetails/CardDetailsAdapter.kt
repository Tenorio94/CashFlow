package com.familyapps.cashflow.application.carddetails

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.maincardsummary.inflate
import com.familyapps.cashflow.application.txndetails.TxnDetailDto
import com.familyapps.cashflow.application.txndetails.TxnDetailsActivity
import org.w3c.dom.Text

class CardDetailsAdapter (private val statementList: ArrayList<CardDetailsTransactionSummary>) :
    RecyclerView.Adapter<CardDetailsAdapter.CardDetailsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailsViewHolder {
        val inflatedView = parent.inflate(R.layout.card_summary_statement_activity, false)
        return CardDetailsViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = statementList.size

    override fun onBindViewHolder(holder: CardDetailsViewHolder, position: Int) {
        val currentTxn: CardDetailsTransactionSummary = statementList[position]

        holder.cardMonthlyTxnDate.setText(currentTxn.txnDate)
        holder.cardMonthlyAmountTxtView.setText(currentTxn.txnMonthAmount)
        holder.cardTxnDescription.setText(currentTxn.txnDescription)
        holder.cardTxnId.setText(currentTxn.txnNumber)
    }

    class CardDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        var cardMonthlyAmountTxtView: TextView
        var cardMonthlyTxnDate: TextView
        var cardTxnDescription: TextView
        var cardTxnId: TextView

        init {
            view.setOnClickListener(this)
            this.cardTxnDescription = itemView.findViewById(R.id.cardSumaryBuying)
            this.cardMonthlyAmountTxtView = itemView.findViewById(R.id.txnAmount)
            this.cardMonthlyTxnDate = itemView.findViewById(R.id.summaryCardTransactionDate)
            this.cardTxnId = itemView.findViewById(R.id.txnId)
        }

        override fun onClick(itemView: View) {
            val context = itemView.context

            val transactionId = Integer.parseInt(itemView.findViewById<TextView>(R.id.txnId).text.toString())
            val transactionDto = TxnDetailDto(transactionId)

            Log.i("CF_CD_TransactionTransfer", String.format("Getting transaction details from id: %s", transactionId))

            val cardDetailsIntent = Intent(context, TxnDetailsActivity::class.java).apply {
                putExtra("extra", transactionDto)
            }

            context.startActivity(cardDetailsIntent)

        }

    }
}