package com.example.secufoam

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart

class DispenserStat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dispenser_statistic)

        val dispenserName = intent.getStringExtra("DISPENSER_NAME")
        val dispenserStatus = intent.getStringExtra("DISPENSER_STATUS")

        findViewById<TextView>(R.id.dispenserName).text = dispenserName
        findViewById<TextView>(R.id.dispenserStatus).text = dispenserStatus
    }
}