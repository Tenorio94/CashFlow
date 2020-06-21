package com.familyapps.cashflow.application.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.familyapps.cashflow.MainActivity
import com.familyapps.cashflow.R
import com.familyapps.cashflow.infraestructure.getSession
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.DbWorkerThread
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var userRepository: UserRepository
    private lateinit var dbWorkerThread: DbWorkerThread

    private val LOG_TAG = "CF_PFE_ProfileEditing"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "Editing Profile Activity Started...")
        cashFlowDb = CashFlowDatabase.getInstance(this)
        userRepository = cashFlowDb.userRepository()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_edit_activity)

        val userEmail = intent.getStringExtra("email")
        val retrievedUser = retrieveUserWithEmail(userEmail)!!
        populateViewTextFields(retrievedUser)
    }

    /**
     * Function used to populate the view with the retrieved data from database corresponding
     * to the user details.
     *
     * @param retrievedUser - User retrieved from the database.
     */
    private fun populateViewTextFields(retrievedUser: User) {
        Log.d(LOG_TAG, String.format("Populating Data for user %s", retrievedUser.email))
        val firstNameEditText = findViewById<EditText>(R.id.profileEditingNameTextEdit)
        val lastNameEditText = findViewById<EditText>(R.id.profileEditingLastNameTextEdit)
        val secondLastNameEditText = findViewById<EditText>(R.id.profileEditingSecondLastNameTextEdit)

        firstNameEditText.setText(retrievedUser.firstName)
        lastNameEditText.setText(retrievedUser.lastName)
        secondLastNameEditText.setText(retrievedUser.secondLastName)
    }

    /**
     * Function used to retrieve the users from the database. There is a guarantee that a user is
     * present since login is required.
     *
     * @param email - String containing the user email for searching as query criteria.
     *
     * @return User fetched from the database.
     */
    private fun retrieveUserWithEmail(userEmail: String) : User? {
        var retrievedUser : User? = null
        Log.d(LOG_TAG, String.format("Retrieving user with email %s", userEmail))

        GlobalScope.launch {
            retrievedUser = userRepository.findUserByEmail(userEmail)

            if (retrievedUser == null) {
                Log.d(LOG_TAG, String.format("Error retrieving user %s, please verify Database", userEmail))
            }
        }
        Thread.sleep(15)
        return retrievedUser
    }

    /**
     * Function used to validate the fields within the view. If any of the fields are missing data
     * it will throw out a toast to notify the user which one is not compliant with the requirement
     *
     * @return Boolean if the form is correct or not.
     */
    private fun validateFields() : Boolean {
        val firstNameEditText = findViewById<EditText>(R.id.profileEditingNameTextEdit).text.toString()
        val lastNameEditText = findViewById<EditText>(R.id.profileEditingLastNameTextEdit).text.toString()
        val passwordEditText = findViewById<EditText>(R.id.profileEditingTextPassword).text.toString()
        val confirmPasswordEditText = findViewById<EditText>(R.id.profileEditingConfirmPasswordTextEdit).text.toString()

        if (firstNameEditText.isEmpty()) {
            Toast.makeText(this, "First Name is blank, please enter your name", Toast.LENGTH_LONG).show()
            return false
        }

        if (lastNameEditText.isEmpty()) {
            Toast.makeText(this, "Last Name is blank, please enter your name", Toast.LENGTH_LONG).show()
            return false
        }

        if (passwordEditText != confirmPasswordEditText) {
            Toast.makeText(this, "Password do no match", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    /**
     * Function used to update the user in the database when the fields are validated correctly. If
     * there is an error within the fields that make them not match the criteria, it will do
     * nothing
     *
     * @param view - Context referencing to the current view.
     */
    fun updateUserinformation(view: View) {
        if (validateFields()) {
            val firstNameEditText = findViewById<EditText>(R.id.profileEditingNameTextEdit).text.toString()
            val lastNameEditText = findViewById<EditText>(R.id.profileEditingLastNameTextEdit).text.toString()
            val secondLastName = findViewById<EditText>(R.id.profileEditingSecondLastNameTextEdit).text.toString()
            val passwordEditText = findViewById<EditText>(R.id.profileEditingTextPassword).text.toString()

            val user = retrieveUserWithEmail(getSession(this, Instant.now()))!!

            user.firstName = firstNameEditText
            user.lastName = lastNameEditText
            user.secondLastName = secondLastName

            if (passwordEditText.isNotBlank()) {
                user.password = passwordEditText
            }

            GlobalScope.launch {
                Log.i(LOG_TAG, String.format("Updating user with email %s to database", user.email))
                userRepository.updateUser(user)
            }
            Thread.sleep(30)

            Toast.makeText(this, "User updated successfully.", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    /**
     * Function used to reset fields of the profile editing so the user can have the default
     * values it previously had prior to editing.
     */
    fun resetFields(view: View) {
        val firstNameEditText = findViewById<EditText>(R.id.profileEditingNameTextEdit)
        val lastNameEditText = findViewById<EditText>(R.id.profileEditingLastNameTextEdit)
        val secondLastNameEditText = findViewById<EditText>(R.id.profileEditingSecondLastNameTextEdit)
        val passwordEditText = findViewById<EditText>(R.id.profileEditingTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.profileEditingConfirmPasswordTextEdit)
        val user = retrieveUserWithEmail(getSession(this, Instant.now()))!!

        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        secondLastNameEditText.setText(user.secondLastName)
        passwordEditText.setText("")
        confirmPasswordEditText.setText("")
    }

    /**
     * Function used to dismiss the keyboard when the user touches any area outside the keyboard
     * that does not correspond to an edit text.
     *
     * @param ev - MotionEvent listening to the touchscreen.
     *
     * @return Boolean corresponding if it is within the area that does not trigger a keyboard.
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus

        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.left - scrcoords[0]
            val y = ev.rawY + v.top - scrcoords[1]

            if (x < v.left || x > v.right || y < v.top || y > v.bottom)
                hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Actual helper method that hides the keyboard from the user.
     *
     * @param activity - Context from which the keyboard is not clicked.
     */
    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null && activity.window.decorView != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}