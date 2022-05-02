package com.example.footballab

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class APIManager : AppCompatActivity(){

    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // This will cause all network traffic to be logged to the console for easy debugging
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    fun retrieveCountryStatistics(fixtureApiKey: String, categoryQuery: String): List<Statistics>{
        val listOfCompetitions = mutableListOf<Statistics>()

        //Requesting the api
        var request: Request = Request.Builder()
            .url("https://transfermarket.p.rapidapi.com/statistic/list-uefa-5year-rankings?countryName=${categoryQuery}")
            .get()
            .addHeader("X-RapidAPI-Host", "transfermarket.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "bda3cee26dmsh185df17cf98c229p1481ddjsn7cbadc8d4afe")
            .build()

        //After waiting the response from the server
        val response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        //If it's successful
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            //Parse the json string
            val json = JSONObject(responseBody)
            val total = json.getJSONArray("teams")
            for (i in 0 until total.length()){
                var curr = total.getJSONObject(i)
                val countryImage  = curr.getString("countryImage")
                val countryName: String = curr.getString("countryName")
                val totalPoints: String = curr.getString("totalPoints")
                val points2021: String = curr.getString("points2021")
                val points2020: String = curr.getString("points2020")
                val points2019: String = curr.getString("points2019")
                val points2018: String = curr.getString("points2018")
                val points2017: String = curr.getString("points2017")

                listOfCompetitions.add(Statistics(countryImage, countryName, totalPoints, points2021, points2020, points2019, points2018, points2017, "Total Points: ", "Year 2021: ","Year 2020: ","Year 2019: ", "Year 2018: ", "Year 2017"))
            }
        }
        return listOfCompetitions
    }
    fun retrieveFixtures(fixtureApiKey: String, categoryQuery: String): List<SourceFixture>{
        val listOfCompetitions = mutableListOf<SourceFixture>()

        //Requesting the api
        val request: Request = Request.Builder()
            .url("https://football-prediction-api.p.rapidapi.com/api/v2/predictions?market=classic&iso_date=2022-05-01&federation=UEFA&competition_name=${categoryQuery}")
            .get()
            .addHeader("X-RapidAPI-Host", "football-prediction-api.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "bda3cee26dmsh185df17cf98c229p1481ddjsn7cbadc8d4afe")
            .build()

        //After waiting the response from the server
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        //If it's successful
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            //Parse the json string
            val json: JSONObject = JSONObject(responseBody)
            val fixtures: JSONArray = json.getJSONArray("data")

            for (i in 0 until fixtures.length()){
                val curr: JSONObject = fixtures.getJSONObject(i)
                val homeTeam: String = curr.getString("home_team")
                val awayTeam: String = curr.getString("away_team")
                val startDate: String = curr.getString("start_date")
                val status: String = curr.getString("status")

                listOfCompetitions.add(SourceFixture(homeTeam, awayTeam, startDate, status))
            }
        }else{
            return emptyList()
        }
        return listOfCompetitions
    }
}
