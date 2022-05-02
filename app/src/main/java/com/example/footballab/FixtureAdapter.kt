package com.example.footballab
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class FixtureAdapter(private val sources: List<SourceFixture>):
    RecyclerView.Adapter<FixtureAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceCard: CardView = itemView.findViewById(R.id.sourceCard)
        val homeTeam: TextView = itemView.findViewById(R.id.homeTeam)
        val awayTeam: TextView = itemView.findViewById(R.id.awayTeam)
        val startDate: TextView = itemView.findViewById(R.id.startDate)
        val status: TextView = itemView.findViewById(R.id.status)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_fixtures, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = sources[position]
        holder.homeTeam.text = currentSource.homeTeam
        holder.awayTeam.text = currentSource.awayTeam
        holder.startDate.text = currentSource.startDate
        holder.status.text = currentSource.status
//        holder.stats.text = currentSource.stats
//        holder.sourceCard.setOnClickListener {
//            var url: Intent = Intent (Intent.ACTION_VIEW, Uri.parse(currentSource.url))
//        }
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