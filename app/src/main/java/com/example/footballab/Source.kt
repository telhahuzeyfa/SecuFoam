package com.example.footballab
import java.io.Serializable

data class Source(val homeTeam: String, val awayTeam: String, val halfTimeScore: String, val currentScore: String, val gameStatus: String) :
    Serializable