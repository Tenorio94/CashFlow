package com.familyapps.cashflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
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
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.familyapps.cashflow.application.login.LoginActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private lateinit var summaryRecLayoutManager: RecyclerView.LayoutManager
    private lateinit var summaryRecyclerView: RecyclerView
    private lateinit var summaryRecViewAdapter: CardSummaryAdapter

    private var cardSummaryList: ArrayList<CardSummaryStatement> = arrayListOf()
    private val LOG_TAG = "CF_MA_MainCreate"

    companion object {
        var currentMonth = LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.US)
        var email = "gtenoriocastillo@gmail.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val cashFlowDb: CashFlowDatabase? = CashFlowDatabase.getInstance(this)
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
            Log.d(LOG_TAG, String.format("Id of resource: %s", R.drawable.ic_android.toString()))
            populateBanks(cashFlowDb)
            populateCards(cashFlowDb)
            cardSummaryList = populateStatements(cashFlowDb)
            createRecycleView()

            cardSummaryList.forEach {
                Log.i(LOG_TAG, String.format("%s statement for %s card.", it.cardSummaryStatement, it.cardSummaryName))
            }
        }
    }

    private fun createRecycleView() {
        summaryRecyclerView = findViewById(R.id.cardSummaryRecyclerView)
        summaryRecyclerView.setHasFixedSize(true)

        summaryRecLayoutManager = LinearLayoutManager(this)
        summaryRecyclerView.layoutManager = summaryRecLayoutManager

        summaryRecViewAdapter = CardSummaryAdapter(cardSummaryList)
        summaryRecyclerView.adapter = summaryRecViewAdapter
    }

    fun verifyUserLoggedIn() : Boolean {
        if (email.isNullOrEmpty() || email.equals("gtenoriocastillo@gmail.com")) {
            Log.i(LOG_TAG, "User not logged in... Redirecting to login.")
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
            return false
        }
        return true
    }

    fun addCard(view: View) {
        cardSummaryList.add(
            CardSummaryStatement(
                R.drawable.ic_flight_land, "$6,780.00", "Banncomer Azul", "4"
            )
        )
        Log.i("CreateRV", cardSummaryList[cardSummaryList.size - 1].cardSummaryName)
        summaryRecViewAdapter.notifyItemInserted(cardSummaryList.size - 1)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_cards -> {
                Toast.makeText(this, "Cards clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
