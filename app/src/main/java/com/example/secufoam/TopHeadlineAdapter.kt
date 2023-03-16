package com.example.footballab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TopHeadlineAdapter(private val headlines: List<TopHeadline>) :
    RecyclerView.Adapter<TopHeadlineAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topHeadlineCardView: CardView = itemView.findViewById(R.id.topHeadlineCard)
        val topHeadlineTitle: TextView = itemView.findViewById(R.id.topHeadlineTitle)
        val publishedAt: TextView = itemView.findViewById(R.id.publishedAt)
        val topHeadlineContents: TextView = itemView.findViewById(R.id.topHeadlineContents)
        val picTemp: ImageView = itemView.findViewById(R.id.picTemp)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_top_headlines, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSource = headlines[position]
        holder.topHeadlineTitle.text = currentSource.title
        holder.topHeadlineContents.text = currentSource.content
        holder.publishedAt.text = currentSource.publishedAt
        //Read the card view search with the link
//        holder.topHeadlineCardView.setOnClickListener {
//            var intUrl: Intent = Intent (Intent.ACTION_VIEW, Uri.parse(currentSource.url))
//            it.context.startActivity(intUrl)
//        }
        Picasso.get().setIndicatorsEnabled(true)
        Picasso
            .get()
            .load(currentSource.url)
            .into(holder.picTemp)
    }
    override fun getItemCount(): Int {
        return headlines.size
    }
    public fun getCheckList(): ArrayList<String> {
        return pendingCheckedList
    }
}