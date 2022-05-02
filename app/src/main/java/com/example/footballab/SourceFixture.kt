package com.example.footballab
import java.io.Serializable

//data class SourceFixture(val homeTeam: String, val awayTeam: String, val date: String) :
//    Serializable
data class SourceFixture(val homeTeam: String, val awayTeam: String, val startDate: String, val status: String) :
    Serializable