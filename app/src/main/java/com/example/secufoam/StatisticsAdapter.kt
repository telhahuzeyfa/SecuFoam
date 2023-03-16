package com.example.footballab
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter(private val statistics: List<Statistics>):
    RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avgTemp: TextView = itemView.findViewById(R.id.avgTemp)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_statistics, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = statistics[position]
        holder.avgTemp.text = currentSource.avgTemp
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