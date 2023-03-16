package com.example.secufoam

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync


class CountryStatistics : AppCompatActivity(){
    lateinit var spinnerCategoryDashboard1: Spinner
    lateinit var standingRecycler: RecyclerView
    lateinit var standingAdapter: StatisticsAdapter
    lateinit var selectSourcesText1: TextView


    //Initially selected competition
    private var selectedCountry = 0

    var selectedSources = arrayListOf<String>()

//    val standingApi = getString(R.string.standingApi)

    companion object{
        var listOfCountries = arrayOf("Seattle", "Virginia", "Washington", "Maryland", "California", "Massachusetts", "Florida", "Pennsylvania")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_country_statistics)
        this.title = "SecuFoam"
        val standingApi = getString(R.string.standingApi)


        spinnerCategoryDashboard1 = findViewById(R.id.spinnerCategoryDashboard1)
        standingRecycler = findViewById(R.id.standingRecycler)
        selectSourcesText1 = findViewById(R.id.selectSourcesText1)

        this.title = "Average Temperature"

        //Initialize the spinner adapter
        val categorySpinnerAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOfCountries
            )
        //Initialize the category spinner
        spinnerCategoryDashboard1.adapter = categorySpinnerAdapter
        spinnerCategoryDashboard1.setSelection(selectedCountry)

        spinnerCategoryDashboard1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectSourcesText1.text = "No Location Selected"
                selectSourcesText1.text = "All"
                selectedCountry = position

                val apiManager = APIManager()

                doAsync {
                    val source: List<Statistics> =
                        apiManager.retrieveCountryStatistics(standingApi, listOfCountries[selectedCountry])
                    standingAdapter = StatisticsAdapter(source)
                    runOnUiThread {
                        selectedSources = standingAdapter.getCheckList()
                        standingRecycler.adapter = standingAdapter
                        //Maybe the home page from here
                        standingRecycler.layoutManager = LinearLayoutManager(this@CountryStatistics)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}