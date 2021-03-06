package com.familyapps.cashflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.familyapps.cashflow.application.maincardsummary.CardSummaryAdapter
import com.familyapps.cashflow.application.maincardsummary.CardSummaryStatement
import com.familyapps.cashflow.infraestructure.databaseextensions.*
import com.familyapps.cashflow.model.CashFlowDatabase
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.widget.TextView
import com.familyapps.cashflow.application.cards.ViewCardsActivity
import com.familyapps.cashflow.application.login.LoginActivity
import com.familyapps.cashflow.application.profile.ProfileActivity
import com.familyapps.cashflow.infraestructure.convertCashToDouble
import com.familyapps.cashflow.infraestructure.convertDoubleToCash
import com.familyapps.cashflow.infraestructure.deleteSession
import com.familyapps.cashflow.infraestructure.getSession
import kotlinx.android.synthetic.main.content_main.*
import java.time.Instant


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private lateinit var summaryRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var summaryRecyclerView: RecyclerView
    private lateinit var summaryRecViewAdapter: CardSummaryAdapter

    private var cardSummaryList: ArrayList<CardSummaryStatement> = arrayListOf()
    private val logTag = "CF_MA_MainCreate"

    companion object {
        var currentMonth = LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.US)
        var email = "gtenoriocastillo@gmail.com"
    }

    /**
     * Main function when this activity is invoked. Weather it is when loading the application
     * or when it is being called after a login/register has been performed successfully. This
     * function is the starter function of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val cashFlowDb: CashFlowDatabase? = CashFlowDatabase.getInstance(this)
        var monthlyStatement: Double = 0.0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        if (verifyUserLoggedIn()) {
            Log.d(logTag, String.format("Id of resource: %s", R.drawable.ic_android.toString()))
            populateBanks(cashFlowDb)
            populateCards(cashFlowDb)
            cardSummaryList = populateStatements(cashFlowDb)
            createRecycleView()

            cardSummaryList.forEach {
                monthlyStatement += convertCashToDouble(it.cardSummaryStatement)
                Log.i(logTag, String.format("%s statement for %s card.", it.cardSummaryStatement, it.cardSummaryName))
            }
        }
        val monthlyStatmentTextView: TextView = this.monthlyMoneyTxtView
        monthlyStatmentTextView.text = convertDoubleToCash(monthlyStatement)
    }

    /**
     * Function used to populate the view containing the credit card information overview which
     * includes primarily the data of the credit card and amount corresponding to the month
     */
    private fun createRecycleView() {
        summaryRecyclerView = findViewById(R.id.cardSummaryRecyclerView)
        summaryRecyclerView.setHasFixedSize(true)

        summaryRecLayoutManager = LinearLayoutManager(this)
        summaryRecyclerView.layoutManager = summaryRecLayoutManager

        summaryRecViewAdapter = CardSummaryAdapter(cardSummaryList)
        summaryRecyclerView.adapter = summaryRecViewAdapter
    }

    /**
     *
     *  Function used to check if there is an active user Session, in case there is no session the
     *  screen will be redirected to the login page. If there is a session this will continue
     *  loading the main screen which is the card overview.
     *
     */
    private fun verifyUserLoggedIn(): Boolean {
        val userName = getSession(this, Instant.now())
        if (userName == "") {
            Log.i(logTag, "User not logged in... Redirecting to login.")
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
            return false
        }
        Log.i(logTag, String.format("Logging in with user %s", userName))
        return true
    }

    /**
     * Function to add a new Card to the RecyclerView, this is listening to the add button floating
     * in the main view. The purpose of this button is to redirecto to the add new transaction for
     * a list of cards.
     */
    fun addCard(view: View) {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land, "$6,780.00", "Banncomer Azul", "4"
            )
        )
        Log.i("CreateRV", cardSummaryList[cardSummaryList.size - 1].cardSummaryName)
        summaryRecViewAdapter.notifyItemInserted(cardSummaryList.size - 1)
    }

    /**
     * Function controlling the navigation view. There are several buttons to be clicked. Once a
     * button is clicked this will redirect depending on what the action is. Weather it is ending
     * an activity or initializing a new one.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Log.d(logTag, "Redirecting to profile viewing...")
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_cards -> {
                Log.d(logTag, "Redirecting to card viewing...")
                startActivity(Intent(this, ViewCardsActivity::class.java))
            }
            R.id.nav_logout -> {
                Log.i(logTag, "Signing out user...")
                deleteSession(this)
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
