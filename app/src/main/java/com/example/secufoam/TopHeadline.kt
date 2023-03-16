package com.example.footballab

import java.io.Serializable

data class TopHeadline(
    val title: String,
    val publishedAt: String,
    val url: String,
    val content: String,
    val picTemp: String
) : Serializable