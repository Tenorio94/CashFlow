package com.familyapps.cashflow.application.cards

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.infraestructure.databaseextensions.mapCardWithImage
import com.familyapps.cashflow.infraestructure.getSession
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.card.CreditCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant

class ViewCardsActivity : AppCompatActivity() {

    private var cashFlowDb: CashFlowDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread

    private lateinit var viewCardsRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewCardsRecyclerView: RecyclerView
    private lateinit var viewCardsRecViewAdapter: CardViewAdapter

    private var cardList: ArrayList<ViewCardsAttributes> = ArrayList<ViewCardsAttributes>()
    private val LOGTAG = "CF_VC_ViewCards"

    /**
     * Function used to start and initialize Activity, this method also initializes the database
     * thread worker and populates the RecyclerView.
     *
     * @param savedInstanceState - Ni puta idea.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOGTAG, "Starting Card View Activity...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_cards_activity)

        cashFlowDb = CashFlowDatabase.getInstance(this)
        val userEmail = getSession(this, Instant.now())

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        GlobalScope.launch {
            retrieveCreditCards(userEmail)
        }
        Thread.sleep(50)

        createRecyclerView(cardList)
    }

    /**
     * Method used to query the database for all credit cards corresponding to a user. These cards
     * are then converted into ViewCardAttributes which are populated in the RecyclerView.
     *
     * @param userEmail - Email as identifier for all the Credit Cards.
     */
    private fun retrieveCreditCards(userEmail: String) {
        Log.d(LOGTAG, String.format("Retrieving cards for email %s", userEmail))
        val creditCardRepository = cashFlowDb?.cardRepository()
        val creditCardsList = creditCardRepository!!.findAllCreditCardsByEmail(userEmail)
        Thread.sleep(15)
        for (card in creditCardsList) {
            Log.d(LOGTAG, String.format("Card %s in list", card.bankCardName))
        }
        createAttributeArrayList(creditCardsList)
        Log.d(LOGTAG, String.format("Cards retrieved for email %s", userEmail))
    }

    /**
     * Method used to extract the pertinent attributes for the RecyclerView, in this case being
     * the Credit Card Name, Credit Card Number, and Image Resource.
     *
     * @param creditCardList - List of credit cards extracted from the database.
     */
    private fun createAttributeArrayList(creditCardList: List<CreditCard>) {
        Log.d(LOGTAG, String.format("Creating attribute list from Card List..."))
        for (card in creditCardList) {
            val cardImage = assignCardImage(card)
            val cardName = card.cardName
            val cardNumber = card.cardNumber
            val viewCardsAttributes = ViewCardsAttributes(cardImage, cardNumber, cardName)

            cardList.add(viewCardsAttributes)
            Log.d(LOGTAG, String.format("%s added to the List", viewCardsAttributes.toString()))
        }
    }

    /**
     * Method used to create the RecyclerView's properties as well as assigning a Layout Manager and
     * the Adapter to manipulate the rows.
     *
     * @param cardList - Extracted attributes from the Credit Cards to pupulate RecyclerView.
     */
    private fun createRecyclerView(cardList: ArrayList<ViewCardsAttributes>) {
        viewCardsRecyclerView = findViewById(R.id.viewCardsRecyclerView)
        viewCardsRecyclerView.setHasFixedSize(true)

        viewCardsRecLayoutManager = LinearLayoutManager(this)
        viewCardsRecyclerView.layoutManager = viewCardsRecLayoutManager

        viewCardsRecViewAdapter = CardViewAdapter(cardList)
        viewCardsRecyclerView.adapter = viewCardsRecViewAdapter
    }

    /**
     * Method used to convert the id of the card image resource and get the resource ID corresponding
     * to that credit card.
     *
     * @param creditCard - Credit Card retrieved from the database.
     *
     * @return Integer representing the resource ID for the card image.
     */
    private fun assignCardImage(creditCard: CreditCard): Int {
        val creditCardBankName = creditCard.bankCardName
        return mapCardWithImage(creditCardBankName)
    }

    /**
     * Function used to add a New Credit Card. This will redirect to a different Activity to upload
     * the new credit card.
     *
     * @param view - View context of the whole activity.
     */
    fun addCard(view: View) {
        cardList.add(ViewCardsAttributes(R.drawable.amex_platinum_card, "4", "Bancomer Azul"))
        Log.i(LOGTAG, String.format("Created Card %s", cardList[cardList.size - 1].viewCardCardName))
        viewCardsRecViewAdapter.notifyItemInserted(cardList.size - 1)
    }

}