package com.example.secufoam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sp

class MainActivity : AppCompatActivity(){

    private lateinit var institutionsSpinner: Spinner
    private lateinit var institutionRecycler: RecyclerView
    private lateinit var institutionAdapter: InstitutionAdapter
    private lateinit var viewMapButton: Button


    //George Washington University will be the initially selected institution by default
    private var selectedInstitution = 0;


    //List of institutions - further update
    companion object {
        var listOfInstitutions = arrayOf("GWU", "American", "Georgetown", "GMU", "JMU", "Virginia Tech")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_landing_page)

        val sharedPrefs: SharedPreferences = getSharedPreferences("SecuFoam", Context.MODE_PRIVATE)

        institutionsSpinner = findViewById(R.id.institutionsSpinner)
        institutionRecycler = findViewById(R.id.institutionRecycler)
        viewMapButton = findViewById(R.id.viewMap)
        val institutionApi = R.string.institutionApi

        this.title = "SecuFoam"

        //Initialize the category adapter
        val categoryInstitutionAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOfInstitutions
            )
       //Initialize category spinner
        institutionsSpinner.adapter = categoryInstitutionAdapter
        institutionsSpinner.setSelection(selectedInstitution)

        institutionsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                selectSourcesText1.text = "No Location Selected"
//                selectSourcesText1.text = "All"
                selectedInstitution = position

                val apiManager = APIManager()

                doAsync {
                    val institution: List<Institutions> =
                        apiManager.hallOneGwu(institutionApi, MainActivity.listOfInstitutions[selectedInstitution])
                    institutionAdapter = InstitutionAdapter(institution)
                    runOnUiThread {
//                        selectedSources = standingAdapter.getCheckList()
                        institutionRecycler.adapter = institutionAdapter
                        //Maybe the home page from here
                        institutionRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        //Go to the map page
        viewMapButton.setOnClickListener { view: View ->
            Toast.makeText( getBaseContext(), "Long Press on the map",Toast.LENGTH_SHORT).show();
            val intent: Intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}