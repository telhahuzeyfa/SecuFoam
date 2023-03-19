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

class MainActivity : AppCompatActivity(){

    private lateinit var institutionRecycler: RecyclerView
    private lateinit var hallsAdapter: HallsAdapter
    private lateinit var viewMapButton: Button
    private lateinit var hallsSpinner: Spinner

    //The first hall selected
    private var selectedHall = 0;


    //List of institutions - further update
    companion object {
        var listOfInstitutions = arrayOf("SEH", "USC", "South", "District", "Elliot", "Tomkins")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_landing_page)

        val sharedPrefs: SharedPreferences = getSharedPreferences("SecuFoam", Context.MODE_PRIVATE)

        hallsSpinner = findViewById(R.id.hallsSpinner)
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
        hallsSpinner.adapter = categoryInstitutionAdapter
        hallsSpinner.setSelection(selectedHall)

        hallsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                selectSourcesText1.text = "No Location Selected"
//                selectSourcesText1.text = "All"
                selectedHall = position

                val apiManager = APIManager()

                doAsync {
                    val institution: List<Institutions> =
                        apiManager.hallOneGwu(institutionApi, MainActivity.listOfInstitutions[selectedHall])
                    hallsAdapter = HallsAdapter(institution)
                    runOnUiThread {
//                        selectedSources = standingAdapter.getCheckList()
                        institutionRecycler.adapter = hallsAdapter
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