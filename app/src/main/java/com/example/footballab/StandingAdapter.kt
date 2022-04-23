package com.example.footballab
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StandingAdapter(private val standings: List<Standings>):
    RecyclerView.Adapter<StandingAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = itemView.findViewById(R.id.teamName)
        val teamRank: TextView = itemView.findViewById(R.id.teamRank)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_standings, parent, false)

        return ViewHolder(itemView)
    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        // Put the data in the each rows
//        val currentSource = standings[position]
//        holder.teamName.text = currentSource.teamName
//        holder.teamRank.text = currentSource.teamRank
//    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = standings[position]
        holder.teamName.text = currentSource.teamName
        holder.teamRank.text = currentSource.teamRank
        //Read the card view search with the link
//        holder.topHeadlineCardView.setOnClickListener {
//            var intUrl: Intent = Intent (Intent.ACTION_VIEW, Uri.parse(currentSource.iconUrl))
//            it.context.startActivity(intUrl)
//        }
//        Picasso.get().setIndicatorsEnabled(true)
//        Picasso
//            .get()
//            .load(currentSource.iconUrl)
//            .into(holder.topHeadlineThumbnail)
    }
    override fun getItemCount(): Int {
        return standings.size
    }
    public fun getCheckList(): ArrayList<String> {
        return pendingCheckedList
    }
    public fun checkListSize(): Int {
        return pendingCheckedList.size
    }
}