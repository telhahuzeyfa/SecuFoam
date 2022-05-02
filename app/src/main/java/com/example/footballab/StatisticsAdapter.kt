package com.example.footballab
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter(private val statistics: List<Statistics>):
    RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryImage: ImageView = itemView.findViewById(R.id.countryImage)
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val totalPoints: TextView = itemView.findViewById(R.id.totalPoints)
        val points2021: TextView = itemView.findViewById(R.id.points2021)
        val points2020: TextView = itemView.findViewById(R.id.points2020)
        val points2019: TextView = itemView.findViewById(R.id.points2019)
        val points2018: TextView = itemView.findViewById(R.id.points2018)
        val points2017: TextView = itemView.findViewById(R.id.points2017)
        val totalPointString: TextView = itemView.findViewById(R.id.totalPointString)
        val year2021: TextView = itemView.findViewById(R.id.year2021)
        val year2020: TextView = itemView.findViewById(R.id.year2020)
        val year2019: TextView = itemView.findViewById(R.id.year2019)
        val year2018: TextView = itemView.findViewById(R.id.year2018)
        val year2017: TextView = itemView.findViewById(R.id.year2017)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_statistics, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = statistics[position]
        holder.countryName.text = currentSource.countryName
        holder.totalPoints.text = currentSource.totalPoints
        holder.points2021.text = currentSource.points2021
        holder.points2020.text = currentSource.points2020
        holder.points2019.text = currentSource.points2019
        holder.points2018.text = currentSource.points2018
        holder.points2017.text = currentSource.points2017
        holder.totalPointString.text = currentSource.totalPointString
        holder.year2021.text = currentSource.year2021
        holder.year2020.text = currentSource.year2020
        holder.year2019.text = currentSource.year2019
        holder.year2018.text = currentSource.year2018
        holder.year2017.text = currentSource.year2017
    }
    override fun getItemCount(): Int {
        return statistics.size
    }
    public fun getCheckList(): ArrayList<String> {
        return pendingCheckedList
    }
    public fun checkListSize(): Int {
        return pendingCheckedList.size
    }
}