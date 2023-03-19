package com.example.secufoam

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SelectInstitution: AppCompatActivity() {
    private lateinit var institutionsSpinner: Spinner
    private lateinit var continueButton: Button

    //List of institutions - further update
    companion object {
        var listOfInstitutions = arrayOf("George Washington University")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_institution)

        institutionsSpinner = findViewById(R.id.institutionsSpinner)
        continueButton = findViewById(R.id.continueButton)

        //Initialize the category adapter
        val categoryInstitutionAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item,
            listOfInstitutions
            )
        institutionsSpinner.adapter = categoryInstitutionAdapter

        continueButton.setOnClickListener { view: View ->
            val intent: Intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
