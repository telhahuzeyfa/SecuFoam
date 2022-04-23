package com.example.footballab
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SourceAdapter(private val sources: List<Source>):
    RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val homeTeam: TextView = itemView.findViewById(R.id.homeTeam)
        val awayTeam: TextView = itemView.findViewById(R.id.awayTeam)
        val halfTimeScore: TextView = itemView.findViewById(R.id.halfTimeScore)
        val gameStatus: TextView = itemView.findViewById(R.id.gameStatus)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_fixtures, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = sources[position]
        holder.homeTeam.text = currentSource.awayTeam
        holder.awayTeam.text = currentSource.awayTeam
        holder.halfTimeScore.text = currentSource.halfTimeScore
        holder.gameStatus.text = currentSource.gameStatus
    }
    override fun getItemCount(): Int {
        return sources.size
    }
    public fun getCheckList(): ArrayList<String> {
        return pendingCheckedList
    }
    public fun checkListSize(): Int {
        return pendingCheckedList.size
    }
}