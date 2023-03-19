package com.example.secufoam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class InstitutionAdapter(private val institutions: List<Institutions>):
    RecyclerView.Adapter<InstitutionAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val institutionCard: CardView = itemView.findViewById(R.id.institutionCard)
        val institutionTitleSource: TextView = itemView.findViewById(R.id.institutionTitleSource)
        val institutionContents: TextView = itemView.findViewById(R.id.institutionContents)
        val averageTemp: TextView = itemView.findViewById(R.id.averageTemp)
        val totalNumOfDispensers: TextView = itemView.findViewById(R.id.totalNumOfDispensers)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_institution, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Put the data in the each rows
        val currentSelectedInstitution = institutions[position]
//Figure out how to add the image into the holder --> research other projects
//        holder.institutionThumbnail.imageView = currentSelectedInstitution.institutionThumbnail
        holder.institutionTitleSource.text = currentSelectedInstitution.institutionTitleSource
        holder.institutionContents.text = currentSelectedInstitution.institutionContents
        holder.averageTemp.text = currentSelectedInstitution.averageTemp
        holder.totalNumOfDispensers.text = currentSelectedInstitution.numOfDispensers
        holder.institutionCard.setOnClickListener {
            val intent = Intent(holder.itemView.context, DispenserStat::class.java)
            val bundle = Bundle()
            bundle.putString("title", currentSelectedInstitution.institutionTitleSource)
            bundle.putString("contents", currentSelectedInstitution.institutionContents)
            bundle.putString("temperature", currentSelectedInstitution.averageTemp)
            bundle.putString("dispensers", currentSelectedInstitution.numOfDispensers)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return institutions.size
    }
    public fun getCheckList(): ArrayList<String> {
        return pendingCheckedList
    }
    public fun checkListSize(): Int {
        return pendingCheckedList.size
    }
}