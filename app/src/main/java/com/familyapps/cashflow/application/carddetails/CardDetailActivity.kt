package com.familyapps.cashflow.application.carddetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.familyapps.cashflow.R

class CardDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_details_activity)
        //val userId = intent.extras.get("79260547") as String
        val cardDetails = intent.getParcelableExtra<CardDetailDTO>("extra")
        println(cardDetails.cardName)

        findViewById<TextView>(R.id.cardNameDetailsTextView).text = cardDetails.cardName
    }
}