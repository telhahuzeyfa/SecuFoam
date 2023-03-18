package com.example.secufoam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InstitutionAdapter(private val institutions: List<Institutions>):
    RecyclerView.Adapter<InstitutionAdapter.ViewHolder>() {

    private var pendingCheckedList: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val institutionThumbnail: ImageView = itemView.findViewById(R.id.institutionThumbnail)
        val institutionTitleSource: TextView = itemView.findViewById(R.id.institutionTitleSource)
        val institutionContents: TextView = itemView.findViewById(R.id.institutionContents)
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