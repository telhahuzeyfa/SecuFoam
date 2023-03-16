package com.example.secufoam

import java.io.Serializable

data class TopHeadline(
    val title: String,
    val publishedAt: String,
    val url: String,
    val content: String,
    val picTemp: String
) : Serializable