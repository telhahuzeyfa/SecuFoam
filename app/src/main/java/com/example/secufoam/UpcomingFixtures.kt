package com.example.footballab

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class UpcomingFixtures : AppCompatActivity(){
    lateinit var spinnerCategory: Spinner
    lateinit var fixtureRecycler: RecyclerView
    lateinit var sourceAdapter: FixtureAdapter
    lateinit var skipSourceButton: Button
    lateinit var selectSourcesText: TextView

    //Initially selected competition
    private var selectedCompetition = 0

    var selectedSources = arrayListOf<String>()


    companion object{
        var listOfCompetitions = arrayOf("SEH", "Tompkins Hall", "Elliott", "USC", "District Hall")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_fixtures)
        this.title = "SecuFoam"

        val fixtureApiKey = getString(R.string.fixtureApiKey)

        spinnerCategory = findViewById(R.id.spinnerCategoryDashboard)
        fixtureRecycler = findViewById(R.id.fixtureRecycler)
        selectSourcesText = findViewById(R.id.selectSourcesText)

        //Initialize the spinner adapter
        val categorySpinnerAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOfCompetitions
            )
        //Initialize the category spinner
        spinnerCategory.adapter = categorySpinnerAdapter
        spinnerCategory.setSelection(selectedCompetition)

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectSourcesText.text = "0 location Selected"
                selectSourcesText.text = "Locations (select at least 1)"
                selectedCompetition = position
                val newsManger = APIManager()
                doAsync {
                    val sources: List<SourceFixture> =
                        newsManger.retrieveFixtures(fixtureApiKey, listOfCompetitions[selectedCompetition])
                    sourceAdapter = FixtureAdapter(sources)
                    runOnUiThread {
                        selectedSources = sourceAdapter.getCheckList()
                        fixtureRecycler.adapter = sourceAdapter
                        fixtureRecycler.layoutManager = LinearLayoutManager(this@UpcomingFixtures)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
//        skipSourceButton.setOnClickListener { view: View ->
//
//            val newsManger = APIManager()
//            doAsync {
//                for (element in listOfCompetitions){
//                    val sources: List<Source> =
//                        newsManger.retrieveSources(newsAPIKey, element)
//                    sourceAdapter = SourceAdapter(sources)
//                }
//                runOnUiThread {
//                    selectedSources = sourceAdapter.getCheckList()
//                    sourcesRecyclerView.adapter = sourceAdapter
//                    sourcesRecyclerView.layoutManager = LinearLayoutManager(this@SourceScreen)
//                }
//            }
//        }
    }
}