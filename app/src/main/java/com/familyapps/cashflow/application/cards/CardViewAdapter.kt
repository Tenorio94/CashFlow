package com.familyapps.cashflow.application.cards

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.maincardsummary.inflate

class CardViewAdapter (private val cardList: ArrayList<ViewCardsAttributes>) :
    RecyclerView.Adapter<CardViewAdapter.ViewCardViewHolder>() {

    private val LOG_TAG = "CF_VC_ViewCardAdapter"

    /**
     * Function used to load the RecyclerView with the data that will pop-up. During this
     * process the view is inflated with the data content loaded in the view_cards_recycle_view
     * activity and returns the new view with the attached elements.
     *
     * @param parent - ViewGroup containing the RecyclerView Itself.
     * @param viewType - Type of View
     *
     * @return ViewCardViewHolder inflated with attached elements.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCardViewHolder {
        val inflatedView = parent.inflate(R.layout.view_cards_recycle_view, false)
        return ViewCardViewHolder(inflatedView)
    }

    /**
     * Scanner containing the size and amount of elements that will populate the Recycler View
     *
     * @return - CardList size/length
     */
    override fun getItemCount(): Int = cardList.size

    /**
     * Binder used to populate the elements on the contents of the Recycler View. These bindings
     * set the text and images of the credit cards for this specific scenario.
     *
     * @param holder - ViewCardViewHolder containing the individual rows of data representing the
     * view_cards_recycler_view content.
     * @param position - Integer indicating the selected element or element being populated.
     *
     */
    override fun onBindViewHolder(holder: ViewCardViewHolder, position: Int) {
        val currentCardView: ViewCardsAttributes = cardList[position]

        holder.viewCardImageView.setImageResource(currentCardView.viewCardImageResource)
        holder.viewCardCardNameTextView.text = currentCardView.viewCardCardName
        holder.viewCardCardNumberTextView.text = currentCardView.viewCardNumber

        holder.viewCardEditButton.setOnClickListener {
            Log.d(LOG_TAG, String.format("Edit Button Clicked for %s. Launching Edit Screen...", currentCardView.viewCardCardName))
            Toast.makeText(it.context, String.format("Edit Button Pressed for %s", holder.viewCardCardNameTextView.text), Toast.LENGTH_SHORT).show()
        }

        holder.viewCardDeleteButton.setOnClickListener {
            Log.d(LOG_TAG, String.format("Delete Button Clicked for %s. Deactivating Card...", currentCardView.viewCardCardName))
            Toast.makeText(it.context, String.format("Delete Button Pressed for %s", holder.viewCardCardNameTextView.text), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Inner Class used to initialize the RecyclerView individual elements and handle listeners
     * depending on the behavior the user wants to specify.
     *
     * @param itemView - View on the element clicked by the user in the Recycler View.
     */
    class ViewCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view = itemView
        var viewCardImageView: ImageView
        var viewCardCardNameTextView: TextView
        var viewCardCardNumberTextView: TextView
        var viewCardEditButton: ImageButton
        var viewCardDeleteButton: ImageButton

        /**
         * ViewHolder initializer that will assign the values of each of the row's properties as
         * well as defining the on Click listeners of the buttons.
         */
        init {
            view.setOnClickListener(this)
            this.viewCardCardNameTextView = itemView.findViewById(R.id.cardViewCardNameTextView)
            this.viewCardCardNumberTextView = itemView.findViewById(R.id.cardViewCardNumber)
            this.viewCardImageView = itemView.findViewById(R.id.cardViewCardImageView)
            this.viewCardEditButton = itemView.findViewById(R.id.cardViewEditButton)
            this.viewCardDeleteButton = itemView.findViewById(R.id.cardViewDeleteButton)
        }

        /**
         * Listener that will intercept the touch feedback of the user on the row it is clicking.
         * Depending on the row is the information to be shown..
         *
         * @param itemView - View containing the information of the card shown.
         */
        override fun onClick(itemView: View) {
            val context = itemView.context
            Toast.makeText(context, String.format("Card Name: %s Clicked.", viewCardCardNameTextView.text), Toast.LENGTH_SHORT).show()
        }

    }
}