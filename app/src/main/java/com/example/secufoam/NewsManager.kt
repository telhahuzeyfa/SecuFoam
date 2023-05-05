package com.example.secufoam

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class NewsManager {

    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
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
                    .url("https://newsapi.org/v2/everything?q=covid19&qInTitle=${address.first().adminArea}")
                    .header("Authorization", "$newsApiKey")
                    .build()
            }
            else -> {
                Request.Builder()
                    .url("https://newsapi.org/v2/everything?q=covid19&qInTitle=${address.first().countryName}")
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
