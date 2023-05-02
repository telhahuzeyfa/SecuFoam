package com.example.secufoam


import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
        val request = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=${categoryQuery}")
            .get()
            .addHeader("X-RapidAPI-Key", "bda3cee26dmsh185df17cf98c229p1481ddjsn7cbadc8d4afe")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()

        //After waiting the response from the server
        val response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        //If it's successful
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            //Parse the json string
            val json: JSONObject = JSONObject(responseBody)

            val humidity: String = json.getString("humidity")

            listOfCompetitions.add(Statistics("Average Temperature: $humidity"))
        }else{
            return emptyList()
        }
        return listOfCompetitions
    }
    fun retrieveFixtures(fixtureApiKey: String, categoryQuery: String): List<SourceFixture>{
        val listOfCompetitions = mutableListOf<SourceFixture>()

        //Requesting the api
        val request: Request = Request.Builder()
                //Dispenser one API - GWU
            .url("https://api.countapi.xyz/hit/secufoam.com")
            .get()
            .build()

        //After waiting the response from the server
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        //If it's successful
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            //Parse the json string
            val json: JSONObject = JSONObject(responseBody)

            val numValue: String = json.getString("value")

            listOfCompetitions.add(SourceFixture("Total Activation: $numValue"))
        }else{
            return emptyList()
        }
        return listOfCompetitions
    }
    fun hallOneGwu(fixtureApiKey: Int, categoryQuery: String): List<Institutions>{
        val listOfCompetitions = mutableListOf<Institutions>()



        val dispenser1 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Oregon")
            .get()
            .addHeader("X-RapidAPI-Key", "c7e8441dddmsh9dd8cc5e2a1ee91p134a62jsn512c134bb342")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()

        val dispenser2 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Seattle")
            .get()
            .addHeader("X-RapidAPI-Key", "c7e8441dddmsh9dd8cc5e2a1ee91p134a62jsn512c134bb342")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()

        val dispenser3 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Alaska")
            .get()
            .addHeader("X-RapidAPI-Key", "c7e8441dddmsh9dd8cc5e2a1ee91p134a62jsn512c134bb342")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()
        val dispenser4 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Oregon")
            .get()
            .addHeader("X-RapidAPI-Key", "c7e8441dddmsh9dd8cc5e2a1ee91p134a62jsn512c134bb342")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()
        val dispenser5 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=California")
            .get()
            .addHeader("X-RapidAPI-Key", "c7e8441dddmsh9dd8cc5e2a1ee91p134a62jsn512c134bb342")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()

        //After waiting the response from the server
//        val response: Response = okHttpClient.newCall(request).execute()
//        val response1: Response = okHttpClient.newCall(request1).execute()
        val responseDispenser1: Response = okHttpClient.newCall(dispenser1).execute()
        val responseDispenser2: Response = okHttpClient.newCall(dispenser2).execute()
        val responseDispenser3: Response = okHttpClient.newCall(dispenser3).execute()
        val responseDispenser4: Response = okHttpClient.newCall(dispenser4).execute()
        val responseDispenser5: Response = okHttpClient.newCall(dispenser5).execute()

//        val responseBody: String? = response.body?.string()
//        val responseBody1: String? = response1.body?.string()
        val responseBodyDispenser1: String? = responseDispenser1.body?.string()
        val responseBodyDispenser2: String? = responseDispenser2.body?.string()
        val responseBodyDispenser3: String? = responseDispenser3.body?.string()
        val responseBodyDispenser4: String? = responseDispenser4.body?.string()
        val responseBodyDispenser5: String? = responseDispenser5.body?.string()

        //If it's successful
        if ((responseDispenser1.isSuccessful && !responseBodyDispenser1.isNullOrEmpty())){
            val jsonDispenser1: JSONObject = JSONObject(responseBodyDispenser1)

            val humidity: String = jsonDispenser1.getString("humidity")
            val totalActivation: String = jsonDispenser1.getString("cloud_pct")

            listOfCompetitions.add(Institutions(categoryQuery, "Total Activations: $totalActivation", "Average Temperature: $humidity", "Dispenser: 1", humidity, totalActivation))
        }
        if ((responseDispenser2.isSuccessful && !responseBodyDispenser2.isNullOrEmpty())) {
            val jsonDispenser2: JSONObject = JSONObject(responseBodyDispenser2)

            val humidity: String = jsonDispenser2.getString("humidity")
            val totalActivation: String = jsonDispenser2.getString("cloud_pct")

            listOfCompetitions.add(Institutions(categoryQuery, "Total Activations: $totalActivation", "Average Temperature: $humidity", "Dispenser: 2", humidity, totalActivation))
        }
        if ((responseDispenser3.isSuccessful && !responseBodyDispenser3.isNullOrEmpty())) {
            val jsonDispenser3: JSONObject = JSONObject(responseBodyDispenser3)

            val humidity: String = jsonDispenser3.getString("humidity")
            val totalActivation: String = jsonDispenser3.getString("cloud_pct")

            listOfCompetitions.add(Institutions(categoryQuery, "Total Activations: $totalActivation", "Average Temperature: $humidity", "Dispenser: 3", humidity, totalActivation))
        }
        if ((responseDispenser4.isSuccessful && !responseBodyDispenser4.isNullOrEmpty())) {
            val jsonDispenser4: JSONObject = JSONObject(responseBodyDispenser4)

            val humidity: String = jsonDispenser4.getString("humidity")
            val totalActivation: String = jsonDispenser4.getString("cloud_pct")

            listOfCompetitions.add(Institutions(categoryQuery, "Total Activations: $totalActivation", "Average Temperature: $humidity", "Dispenser: 4", humidity, totalActivation))
        }
        if ((responseDispenser5.isSuccessful && !responseBodyDispenser5.isNullOrEmpty())) {
            val jsonDispenser4: JSONObject = JSONObject(responseBodyDispenser5)

            val humidity: String = jsonDispenser4.getString("humidity")
            val totalActivation: String = jsonDispenser4.getString("cloud_pct")

            listOfCompetitions.add(Institutions(categoryQuery, "Total Activations: $totalActivation", "Average Temperature: $humidity", "Dispenser: 5", humidity, totalActivation))
        }
        else{
            return emptyList()
        }
        return listOfCompetitions
    }
}
