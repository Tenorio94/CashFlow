package com.familyapps.cashflow.application.profile

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.familyapps.cashflow.R
import com.familyapps.cashflow.infraestructure.getSession
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.time.Instant

class ProfileActivity : AppCompatActivity() {
    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var userRepository: UserRepository
    private lateinit var dbWorkerThread: DbWorkerThread

    private val LOG_TAG = "CF_PF_ProfileView"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "Viewing Profile Activity Started...")
        cashFlowDb = CashFlowDatabase.getInstance(this)
        userRepository = cashFlowDb.userRepository()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view_activity)

        dbWorkerThread = DbWorkerThread("cashFlowDbWorkerThread")
        dbWorkerThread.start()

        val email = getSession(this, Instant.now())
        val user = retrieveUser(email)!!
        populateProfileViewFields(user)
    }

    private fun retrieveUser(email: String) : User? {
        var retrievedUser : User? = null
        Log.d(LOG_TAG, String.format("Retrieving user with email %s", email))

        GlobalScope.launch {
            retrievedUser = userRepository.findUserByEmail(email)

            if (retrievedUser == null) {
                Log.d(LOG_TAG, String.format("Error retrieving user %s, please verify Database", email))
            }
        }
        Thread.sleep(15)
        return retrievedUser
    }

    private fun populateProfileViewFields(user: User) {
        val nameTextView = findViewById<TextView>(R.id.userNameProfileNamePlaceHolder)
        val emailTextView = findViewById<TextView>(R.id.emailProfileViewNamePlaceHolderTextView)

        nameTextView.text = appendAllNames(user)
        emailTextView.text = user.email
    }

    private fun appendAllNames(user: User) : String {
        val firstName = user.firstName
        val lastName = user.lastName
        val secondLastName = user.secondLastName

        if(secondLastName == null)
            return String.format("%s %s", firstName, lastName)

        return String.format("%s %s %s", firstName, lastName, secondLastName)
    }
}