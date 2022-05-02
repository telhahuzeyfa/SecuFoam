package com.example.footballab
import java.io.Serializable

data class Statistics(
    val countryImage: String,
    val countryName: String,
    val totalPoints: String,
    val points2021: String,
    val points2020: String,
    val points2019: String,
    val points2018: String,
    val points2017: String,
    val totalPointString: String,
    val year2021: String,
    val year2020: String,
    val year2019: String,
    val year2018: String,
    val year2017: String
    ) :
    Serializable