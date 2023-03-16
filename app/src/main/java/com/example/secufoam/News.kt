package com.example.footballab

import java.io.Serializable

data class News(
    val title: String,
    val thumbnailUrl: String,
    val source: String,
    val url: String,
    val content: String
): Serializable