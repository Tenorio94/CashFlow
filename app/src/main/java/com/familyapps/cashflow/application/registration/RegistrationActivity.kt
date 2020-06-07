package com.familyapps.cashflow.application.registration

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
import com.familyapps.cashflow.infraestructure.setSession
import com.familyapps.cashflow.model.CashFlowDatabase
import com.familyapps.cashflow.model.user.User
import com.familyapps.cashflow.model.user.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant

class RegistrationActivity : AppCompatActivity() {
    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var userRepository: UserRepository

    private val logTag = "CF_RA_MainRegistration"

    override fun onCreate(savedInstanceState: Bundle?) {
        cashFlowDb = CashFlowDatabase.getInstance(this)
        userRepository = cashFlowDb.userRepository()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration_activity)
    }

    private fun validateFields(): Boolean {
        val registerFirstName = findViewById<EditText>(R.id.registerName).text
        val registerLastName = findViewById<EditText>(R.id.registerLastName).text
        val registerEmail = findViewById<EditText>(R.id.registerEmail).text
        val registerPswd = findViewById<EditText>(R.id.registerPswd).text
        val registerConfPswd = findViewById<EditText>(R.id.registerConfirmPswd).text

        if (registerFirstName.isNullOrBlank()) {
            Toast.makeText(this, "Missing First Name. Please enter full name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registerLastName.isNullOrBlank()) {
            Toast.makeText(this, "Missing Last Name. Please enter full name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registerEmail.isNullOrBlank()) {
            Toast.makeText(this, "Missing Email. Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registerPswd.isNullOrBlank()) {
            Toast.makeText(this, "Missing Password. Please enter your password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registerConfPswd.isNullOrBlank()) {
            Toast.makeText(this, "Missing Password Confirmation.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!validateMatchingPasswords(registerPswd.toString(), registerConfPswd.toString())) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun registerButtonListener(view: View) {
        if (validateFields()) {
            if (validateUserExistence()) {
                Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(logTag, "Registering user...")
                registerUserToDatabase()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    fun clearRegistrationButtonListeners(view: View) {
        clearRegistrationForm()
    }

    private fun clearRegistrationForm() {
        findViewById<EditText>(R.id.registerName).text.clear()
        findViewById<EditText>(R.id.registerLastName).text.clear()
        findViewById<EditText>(R.id.registerEmail).text.clear()
        findViewById<EditText>(R.id.registerPswd).text.clear()
        findViewById<EditText>(R.id.registerConfirmPswd).text.clear()
    }

    private fun validateMatchingPasswords(password: String, passwordConfirmation: String): Boolean {
        return password == passwordConfirmation
    }

    private fun validateUserExistence(): Boolean {
        val registeringMail = findViewById<EditText>(R.id.registerEmail).text.toString()
        var userExists = false

        GlobalScope.launch {
            userExists = userRepository.userExistsByEmail(registeringMail)
        }
        Thread.sleep(30)
        return userExists
    }

    private fun registerUserToDatabase() {
        val registerFirstName = findViewById<EditText>(R.id.registerName).text.toString()
        val registerLastName = findViewById<EditText>(R.id.registerLastName).text.toString()
        val registerEmail = findViewById<EditText>(R.id.registerEmail).text.toString()
        val registerPswd = findViewById<EditText>(R.id.registerPswd).text.toString()

        val registeredUser = User(null, registerFirstName, registerLastName, null, registerEmail, registerPswd)

        GlobalScope.launch {
            Log.i(logTag, String.format("Inserting user with email %s to database", registerEmail))
            userRepository.insertUser(registeredUser)
        }
        Thread.sleep(30)
        setSession(this, registerEmail, registerPswd, Instant.now())
    }

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

    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null && activity.window.decorView != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}