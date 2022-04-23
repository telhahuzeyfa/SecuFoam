package com.example.footballab

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync


class StandingTable : AppCompatActivity(){
    lateinit var spinnerCategoryHome: Spinner
    lateinit var standingRecycler: RecyclerView
    lateinit var standingAdapter: StandingAdapter
    lateinit var selectedNameOfCompetition: TextView


    //Initially selected competition
    private var selectedCompetition = 0

    var selectedSources = arrayListOf<String>()

    val newsAPIKey = getString(R.string.news_api_key)

    companion object{
        var listOfCompetitions = arrayOf("1", "2", "3", "4", "5")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        spinnerCategoryHome = findViewById(R.id.spinnerCategoryHome)
        standingRecycler = findViewById(R.id.standingRecycler)
        selectedNameOfCompetition = findViewById(R.id.selectedNameOfCompetition)

        //Initialize the spinner adapter
        val categorySpinnerAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOfCompetitions
            )
        //Initialize the category spinner
        spinnerCategoryHome.adapter = categorySpinnerAdapter
        spinnerCategoryHome.setSelection(selectedCompetition)

        spinnerCategoryHome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedNameOfCompetition.text = "No Competition Selected"
                selectedNameOfCompetition.text = "Competition (select at least 1)"
                selectedCompetition = position

                val apiManager = APIManager()

                doAsync {
                    val source: List<Standings> =
                        apiManager.retrieveStandings(newsAPIKey, listOfCompetitions[selectedCompetition])
                    standingAdapter = StandingAdapter(source)
                    runOnUiThread {
                        selectedSources = standingAdapter.getCheckList()
                        standingRecycler.adapter = standingAdapter
                        //Maybe the home page from here
                        standingRecycler.layoutManager = LinearLayoutManager(this@StandingTable)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}