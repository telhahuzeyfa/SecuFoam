package com.example.footballab

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.crashlytics.ktx.crashlytics


class CreateAccountActivity: AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var progressBar2: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Layout to use for this case
        setContentView(R.layout.sign_in_activity)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val sharedPrefs: SharedPreferences = getSharedPreferences("FootbalLab", Context.MODE_PRIVATE)

        Log.d("Create Account", "Create Account Called Once")

        progressBar2 = findViewById(R.id.progressBar2)
        registerButton = findViewById(R.id.registerButton)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)

        //Disable register button at first glance
        registerButton.visibility = View.INVISIBLE
//
        registerButton.setOnClickListener {
            val inputtedEmail: String = userEmail.text.toString()
            val inputtedPassword: String = userPassword.text.toString()

//            progressBar2.visibility = View.VISIBLE
            //If the two passwords inputted end up not matching then throw error message

            firebaseAuth
                .createUserWithEmailAndPassword(inputtedEmail, inputtedPassword)
                .addOnCompleteListener { task ->
                    progressBar2.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        firebaseAnalytics.logEvent("Register_clicked", null)
                        val user = firebaseAuth.currentUser
                        Toast.makeText(
                            this,
                            "Successfully registered as ${user!!.email}",
                            Toast.LENGTH_LONG
                        ).show()
                        Toast.makeText(this, "Successfully registered as $inputtedEmail", Toast.LENGTH_LONG).show()
                    }else{
                        val exception = task.exception
                        Toast.makeText(this, "Failed to register $exception", Toast.LENGTH_LONG).show()
                        if (exception != null){
                            Firebase.crashlytics.recordException(exception)
                        }

                        when (exception) {
                            is FirebaseAuthWeakPasswordException -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "weak_password")
                                firebaseAnalytics.logEvent("signup_failed", bundle)
                                Toast.makeText(
                                    this,
                                    R.string.signup_failure_weak_password,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseAuthUserCollisionException -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "existing_account")
                                firebaseAnalytics.logEvent("signup_failed", bundle)
                                Toast.makeText(
                                    this,
                                    R.string.signup_failure_already_exists,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "invalid_credentials")
                                firebaseAnalytics.logEvent("signup_failed", bundle)
                                Toast.makeText(
                                    this,
                                    R.string.signup_failure_invalid_format,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "generic")
                                firebaseAnalytics.logEvent("signup_failed", bundle)
                                Toast.makeText(
                                    this,
                                    getString(R.string.signup_failure_generic, exception),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
        }
    }
}