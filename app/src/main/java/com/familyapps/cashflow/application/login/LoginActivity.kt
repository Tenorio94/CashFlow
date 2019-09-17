package com.familyapps.cashflow.application.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
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

class LoginActivity : AppCompatActivity() {
    private lateinit var cashFlowDb: CashFlowDatabase
    private lateinit var userRepository: UserRepository

    private val logTag = "CF_LA_MainLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        cashFlowDb = CashFlowDatabase.getInstance(this)
        userRepository = cashFlowDb.userRepository()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }

    fun registerUser(view: View) {

    }

    fun logButtonListener(view: View) {
        val emailTxt = findViewById<TextView>(R.id.emailTxt).text.toString()
        val passwordTxt = findViewById<TextView>(R.id.pswdTxt).text.toString()

        if (validateInputs(emailTxt, passwordTxt)) {
            if (validateUser(emailTxt)) {
                if (validatePassword(emailTxt, passwordTxt) != null) {
                    Log.i(logTag, String.format("Logging in with user %s", emailTxt))
                    setSession(this, emailTxt, passwordTxt, Instant.now())
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }
            }
        }
    }

    private fun validatePassword(email: String, password: String): User? {
        var searchedUser: User? = null

        GlobalScope.launch {
            searchedUser = userRepository.findUserByEmail(email)
        }

        Thread.sleep(30)
        if (searchedUser != null) {
            if (searchedUser!!.password.equals(password)) {
                return searchedUser!!
            }
        }
        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
        return null
    }

    private fun validateUser(email: String): Boolean {
        var userExistence = false
        GlobalScope.launch {
            userExistence = userRepository.userExistsByEmail(email)
        }
        Thread.sleep(30)
        if (userExistence) {
            Log.i(logTag, String.format("Validating user %s", email))
            return true
        }
        Toast.makeText(this, "User does not exist, please register...", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "User Email empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isNullOrEmpty()) {
            Toast.makeText(this, "Password Email empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null && activity.window.decorView != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}