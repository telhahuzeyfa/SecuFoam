package com.example.secufoam
import java.io.Serializable

data class Institutions(
    val institutionThumbnail: String,
    val institutionTitleSource: String,
    val institutionContents: String
) : Serializable