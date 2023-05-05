package com.example.secufoam

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class DispenserStat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dispenser_statistic)

        // Get the LineChart view from the layout
        val chart: LineChart = findViewById(R.id.chart)

        // Create an array of Entry objects to hold the data points
        val entries = arrayOf(
            Entry(0f, 100f),
            Entry(1f, 100f),
            Entry(2f, 81f),
            Entry(3f, 100f),
            Entry(4f, 40f),
            Entry(5f, 70f)
        )

        // Create a LineDataSet to hold the data and customize the appearance
        val dataSet = LineDataSet(entries.asList(), "Dispenser Activity")
        dataSet.color = Color.RED
        dataSet.setCircleColor(Color.RED)
        dataSet.lineWidth = 4f
        dataSet.circleRadius = 6f
        dataSet.setDrawValues(true)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.RED
        dataSet.fillAlpha = 40

        // Create a LineData object to hold the LineDataSet
        val lineData = LineData(dataSet)

        // Customize the appearance of the chart
        chart.setDrawGridBackground(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.labelCount = 5
        chart.xAxis.valueFormatter = DispenserAxisValueFormatter()
        chart.xAxis.textColor = Color.BLACK
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.textColor = Color.BLACK
        chart.axisLeft.axisLineColor = Color.BLACK
        chart.axisLeft.setDrawAxisLine(true)
        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        chart.legend.textColor = Color.BLACK
        chart.data = lineData
        chart.invalidate()

        // Get the RecyclerView from the layout
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        // Create an array of Dispenser objects to hold the dispenser status data
        val dispensers = arrayOf(
            Dispenser("1", "Empty", "10:30 AM"),
            Dispenser("2", "Full", "11:05 AM"),
            Dispenser("3", "Almost Empty", "11:45 AM"),
            Dispenser("4", "Half Full", "12:15 PM"),
            Dispenser("5", "Empty", "12:50 PM"),
            Dispenser("6", "Almost Full", "1:20 PM")
        )

        // Create an adapter to display the data in the RecyclerView
        val adapter = DispenserAdapter(dispensers)

        // Set the adapter on the RecyclerView
        recyclerView.adapter = adapter
    }

    class DispenserAxisValueFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "Dispenser ${(value + 1).toInt()}"
        }
    }
    data class Dispenser(val number: String, val status: String, val predictedTime: String)

    class DispenserAdapter(private val dispenserList: Array<Dispenser>) :
        RecyclerView.Adapter<DispenserAdapter.DispenserViewHolder>() {

        inner class DispenserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val dispenserNumber: TextView = itemView.findViewById(R.id.dispenser_number_text)
            val dispenserStatus: TextView = itemView.findViewById(R.id.dispenser_status_text)
            val dispenserPredictedTime: TextView = itemView.findViewById(R.id.dispenser_predicted_time_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispenserViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.dispenser_view_holder, parent, false)
            return DispenserViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: DispenserViewHolder, position: Int) {
            val currentItem = dispenserList[position]
            holder.dispenserNumber.text = currentItem.number
            holder.dispenserStatus.text = currentItem.status
            holder.dispenserPredictedTime.text = currentItem.predictedTime
        }

        override fun getItemCount() = dispenserList.size
    }
}
