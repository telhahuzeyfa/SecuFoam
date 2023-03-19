package com.example.secufoam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


class LoginDirection: AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signUp: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Layout to use for this case
        setContentView(R.layout.sign_in_activity)
        this.title = "SecuFoam"

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val sharedPrefs: SharedPreferences = getSharedPreferences("FootbalLab", Context.MODE_PRIVATE)

        Log.d("LoginDirection", "Login Direction Called Once")

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signUp = findViewById(R.id.signUp)
        progressBar = findViewById(R.id.progressBar)

        login.isEnabled = false
        signUp.isEnabled = false

        //Assuming the login button is clicked we run this block of code
        login.setOnClickListener { view: View ->
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()

            progressBar.visibility = View.VISIBLE

            firebaseAuth
                .signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.INVISIBLE

                    if (task.isSuccessful){
                        firebaseAnalytics.logEvent("login_success", null)

                        val user = firebaseAuth.currentUser
                        Toast.makeText(this, "Logged in as ${user!!.email}", Toast.LENGTH_LONG).show()

                        //Save the username to SharedPreference
                        sharedPrefs
                            .edit()
                            .putString("USERNAME", inputtedUsername)
                            .apply()

                        //Once the user is successfully logged in the will be directed to the Main Activity page which is basically the home page
                        val intent: Intent = Intent(this, SelectInstitution::class.java)
                        startActivity(intent)
                    }else{
                        val exception = task.exception
                        //Clarification needed from lecture recordings
                        if (exception != null){
                            Firebase.crashlytics.recordException(exception)
                        }
                        when (exception) {
                            is FirebaseAuthInvalidUserException -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "no_registered_account")
                                firebaseAnalytics.logEvent("login_failed", bundle)

                                Toast.makeText(
                                    this,
                                    R.string.login_failure_doesnt_exist,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "invalid_credentials")
                                firebaseAnalytics.logEvent("login_failed", bundle)

                                Toast.makeText(
                                    this,
                                    R.string.login_failure_wrong_credentials,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                val bundle = Bundle()
                                bundle.putString("reason", "generic")
                                firebaseAnalytics.logEvent("login_failed", bundle)

                                Toast.makeText(
                                    this,
                                    getString(R.string.login_failure_generic, exception),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
        }
        signUp.setOnClickListener { view: View ->
            val inputtedEmail: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()

            progressBar.visibility = View.VISIBLE
            //If the two passwords inputted end up not matching then throw error message

            firebaseAuth
                .createUserWithEmailAndPassword(inputtedEmail, inputtedPassword)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.INVISIBLE
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
        // Using the same TextWatcher instance for both EditTexts so the same block of code runs on each character.
        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)


        // Restore the saved username from SharedPreferences and display it to the user when the screen loads.
        // Default to the empty string if there is no saved username.
        val savedUsername = sharedPrefs.getString("USERNAME", "")
        username.setText(savedUsername)
    }
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        // We can use any of the three functions -- here, we just use `onTextChanged` -- the goal
        // is the enable the login button only if there is text in both the username & password fields.
        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Kotlin shorthand for username.getText().toString()
            // .toString() is needed because getText() returns an Editable (basically a char array).
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()
            val enableButton: Boolean = inputtedUsername.isNotBlank() && inputtedPassword.isNotBlank()

            // Kotlin shorthand for login.setEnabled(enableButton)
            login.isEnabled = enableButton
            signUp.isEnabled = enableButton
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}