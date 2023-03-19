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
    //Test function
    fun hallOneGwu(fixtureApiKey: Int, categoryQuery: String): List<Institutions>{
        val listOfCompetitions = mutableListOf<Institutions>()

        //Requesting the api
        val request: Request = Request.Builder()
            //Dispenser one API - GWU
            .url("https://api.countapi.xyz/hit/${categoryQuery}")
            .get()
            .build()

        val request1 = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Washington")
            .get()
            .addHeader("X-RapidAPI-Key", "bda3cee26dmsh185df17cf98c229p1481ddjsn7cbadc8d4afe")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()


        //After waiting the response from the server
        val response: Response = okHttpClient.newCall(request).execute()
        val response1: Response = okHttpClient.newCall(request1).execute()

        val responseBody: String? = response.body?.string()
        val responseBody1: String? = response1.body?.string()

        //If it's successful
        if ((response.isSuccessful && !responseBody.isNullOrEmpty()) && (response1.isSuccessful && !responseBody1.isNullOrEmpty())){
            //Parse the json string
            val json: JSONObject = JSONObject(responseBody)
            val json1: JSONObject = JSONObject(responseBody1)

            val numValue: String = json.getString("value")
            val humidity: String = json1.getString("humidity")

            listOfCompetitions.add(Institutions("Science and Engineering Hall", "Total Activations: $numValue", "Average Temperature: $humidity", "Number of Dispenser: 1"))
        }else{
            return emptyList()
        }
        return listOfCompetitions
    }
//    fun hallTneGwu(fixtureApiKey: Int, categoryQuery: String): List<Institutions>{
//        val listOfCompetitions = mutableListOf<Institutions>()
//
//        //Requesting the api
//        val request: Request = Request.Builder()
//            //Dispenser one API - GWU
//            .url("https://api.countapi.xyz/hit/${categoryQuery}")
//            .get()
//            .build()
//
//        //After waiting the response from the server
//        val response: Response = okHttpClient.newCall(request).execute()
//        val responseBody: String? = response.body?.string()
//
//        //If it's successful
//        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
//            //Parse the json string
//            val json: JSONObject = JSONObject(responseBody)
//
//            val numValue: String = json.getString("value")
//
//            listOfCompetitions.add(Institutions("", "George Washington University - USC", "Total Activation: $numValue"))
//        }else{
//            return emptyList()
//        }
//        return listOfCompetitions
//    }
}
