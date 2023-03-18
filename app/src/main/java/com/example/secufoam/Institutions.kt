package com.example.secufoam
import android.widget.ImageView
import android.widget.TextView
import java.io.Serializable

data class Institutions(
    val institutionThumbnail: ImageView,
    val institutionTitleSource: String,
    val institutionContents: String
) : Serializable