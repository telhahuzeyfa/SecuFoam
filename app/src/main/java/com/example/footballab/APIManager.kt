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
    fun fetchMapNews(newsApiKey: String, address: List<android.location.Address>): List<News> {
        val newsList = mutableListOf<News>()
        //build the request
        var request: Request = when {
            address.first().adminArea.isNullOrEmpty() -> {
                return emptyList()
            }
            address.first().countryCode == "US" -> {
                Request.Builder()
                    .url("https://newsapi.org/v2/everything?qInTitle=${address.first().adminArea}")
                    .header("Authorization", "$newsApiKey")
                    .build()
            }
            else -> {
                Request.Builder()
                    .url("https://newsapi.org/v2/everything?qInTitle=${address.first().countryName}")
                    .header("Authorization", "$newsApiKey")
                    .build()
            }
        }
        //call the request
        val response = okHttpClient.newCall(request).execute()
        //retrieve the json body
        val responseString = response.body?.string()
        //check response is not empty or null
        if (response.isSuccessful && !responseString.isNullOrEmpty()) {
            //parse json string
            val json = JSONObject(responseString)
            val articles = json.getJSONArray("articles")
            //parse as News Object to headlines
            for (i in 0 until articles.length()) {
                var currNews = articles.getJSONObject(i)
                val title = currNews.getString("title")
                val thumbnailUrl = currNews.getString("urlToImage")
                val source = currNews.getJSONObject("source").getString("name")
                val url = currNews.getString("url")
                val content = currNews.getString("content")
                newsList.add(News(title, thumbnailUrl, source, url, content))
            }
        }
        return newsList
    }
}
