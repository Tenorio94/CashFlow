package com.familyapps.cashflow.application.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.familyapps.cashflow.R
import com.familyapps.cashflow.application.carddetails.CardDetailActivity
import com.familyapps.cashflow.infraestructure.getSession
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository
import kotlinx.android.synthetic.main.profile_edit_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    /**
     * Function used to retrieve the users from the database. There is a guarantee that a user is
     * present since login is required.
     *
     * @param email - String containing the user email for searching as query criteria.
     *
     * @return User fetched from the database.
     */
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

    /**
     * Function used to populate the textview fields with the data fetched from the database using
     * the user entity.
     *
     * @param user - User fetched from the database from retireveUser method.
     */
    private fun populateProfileViewFields(user: User) {
        val nameTextView = findViewById<TextView>(R.id.userNameProfileNamePlaceHolder)
        val emailTextView = findViewById<TextView>(R.id.emailProfileViewNamePlaceHolderTextView)

        nameTextView.text = appendAllNames(user)
        emailTextView.text = user.email
    }

    /**
     * Function used to generate the full username as in the database it is separated by field of
     * first name, last name and a possible last name which is validated to avoid the showing of
     * null values.
     *
     * @param user - User fetched from the database previously.
     *
     * @return String containing the generated full name of the user.
     */
    private fun appendAllNames(user: User) : String {
        val firstName = user.firstName
        val lastName = user.lastName
        val secondLastName = user.secondLastName

        secondLastName ?: return String.format("%s %s", firstName, lastName)

        return String.format("%s %s %s", firstName, lastName, secondLastName)
    }

    /**
     * Function used to trigger the activity for editing the user profile. This will re-direct the
     * user to a new screen where he/she will be able to change his names as well as his password
     * as often as the user wants.
     *
     * @param view - View used to define a context for using this as an intent laucnher.
     */
    fun triggerEditProfile(view: View) {
        Log.d(LOG_TAG, "Editing profile, initializing activity...")

        val context = view.context
        val userEmail = getSession(this, Instant.now())

        Log.d(LOG_TAG, String.format("Starting activity for email %s", userEmail))

        val profileEmail = Intent(context, ProfileEditActivity::class.java).apply {
            putExtra("email", userEmail)
        }
        context.startActivity(profileEmail)
    }

}