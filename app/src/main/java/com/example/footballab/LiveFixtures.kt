package com.example.footballab
//
//import android.os.Bundle
//import android.view.View
//import android.widget.*
import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
class LiveFixtures : AppCompatActivity(){
//    lateinit var spinnerCategory: Spinner
//    lateinit var fixtureRecycler: RecyclerView
//    lateinit var sourceAdapter: SourceAdapter
//    lateinit var sourcesRecyclerView: RecyclerView
//    lateinit var skipSourceButton: Button
//    lateinit var selectSourcesText: TextView
//
//    //Initially selected competition
//    private var selectedCompetition = 0
//
//    var selectedSources = arrayListOf<String>()
//
//    val newsAPIKey = getString(R.string.news_api_key)
//
//    companion object{
//        var listOfCompetitions = arrayOf("Premier League")
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_dashboard)
////
//        spinnerCategory = findViewById(R.id.spinnerCategoryDashboard)
//        fixtureRecycler = findViewById(R.id.fixtureRecycler)
//        skipSourceButton = findViewById(R.id.skipSourceButton)
//
//        //Initialize the spinner adapter
//        val categorySpinnerAdapter =
//            ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item,
//               listOfCompetitions
//            )
//        //Initialize the category spinner
//        spinnerCategory.adapter = categorySpinnerAdapter
//        spinnerCategory.setSelection(selectedCompetition)
//
//        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                selectSourcesText.text = "0 Sources Selected"
//                selectSourcesText.text = "Sources (select at least 1)"
//                selectedCompetition = position
//                val newsManger = APIManager()
//                doAsync {
//                    val sources: List<Source> =
//                        newsManger.retrieveSources(newsAPIKey, listOfCompetitions[selectedCompetition])
//                    sourceAdapter = SourceAdapter(sources)
//                    runOnUiThread {
//                        selectedSources = sourceAdapter.getCheckList()
//                        sourcesRecyclerView.adapter = sourceAdapter
//                        sourcesRecyclerView.layoutManager = LinearLayoutManager(this@SourceScreen)
//                    }
//                }
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
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
//    }
}