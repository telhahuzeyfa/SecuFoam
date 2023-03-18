package com.example.secufoam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){

    private lateinit var institutionsSpinner: Spinner
    private lateinit var institutionRecycler: RecyclerView
    private lateinit var institutionAdapter: InstitutionAdapter
    private lateinit var viewMapButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_landing_page)

        val sharedPrefs: SharedPreferences = getSharedPreferences("FootbalLab", Context.MODE_PRIVATE)

        viewMapButton = findViewById(R.id.viewMap)
//        upcomingFixtures = findViewById(R.id.upcomingFixtures)
//        standingTable = findViewById(R.id.viewStandingTable)
//        progressBar = findViewById(R.id.progressBar)
        this.title = "SecuFoam"


//        progressBar.visibility = View.INVISIBLE

        //Go to the map page
        viewMapButton.setOnClickListener { view: View ->
            Toast.makeText( getBaseContext(), "Long Press on the map",Toast.LENGTH_SHORT).show();
            val intent: Intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        //Go to the upcoming fixture page
//        upcomingFixtures.setOnClickListener { view: View ->
//            Toast.makeText(getBaseContext(), "Upcoming Fixtures", Toast.LENGTH_LONG).show();
//            val intent: Intent = Intent(this, UpcomingFixtures::class.java)
//            startActivity(intent)
//        }
//        standingTable.setOnClickListener { view: View ->
//            Toast.makeText(getBaseContext(), "Standing Table", Toast.LENGTH_LONG).show();
//            val intent: Intent = Intent(this, CountryStatistics::class.java)
//            startActivity(intent)
//        }
    }
}