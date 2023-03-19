package com.example.secufoam
import java.io.Serializable

data class Institutions(
    val institutionTitleSource: String,
    val institutionContents: String,
    val averageTemp: String,
    val numOfDispensers: String
) : Serializable