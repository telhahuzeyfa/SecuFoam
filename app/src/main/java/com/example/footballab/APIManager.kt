package com.example.footballab

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class APIManager {

    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // This will cause all network traffic to be logged to the console for easy debugging
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }
    fun retrieveStandings(newsApiKey: String, categoryQuery: String): List<Standings>{
        val listOfCompetitions = mutableListOf<Standings>()

        //Requesting the api
        val request: Request = Request.Builder()
            .url("https://livescore-api.com/api-client/leagues/table.json?competition_id=$categoryQuery&key=$newsApiKey&secret=se5PKlRYCuHAbQ7IclpOvAktmnNmmrGP")
            .get()
            .build()

        //After waiting the response from the server
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        //If it's successful
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            //Parse the json string
            val json = JSONObject(responseBody)
            val table = json.getJSONArray("table")

            for (i in 0 until table.length()){
                val curr: JSONObject = table.getJSONObject(i)
                val teamName: String = curr.getString("name")
                val teamRank = curr.getString("rank")

                listOfCompetitions.add(Standings(teamName, teamRank))
            }
        }else{
            return emptyList()
        }
        return listOfCompetitions
    }
}
