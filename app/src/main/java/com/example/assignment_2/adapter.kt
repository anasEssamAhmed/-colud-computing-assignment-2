package com.example.assignment_2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_pdf.view.*

class adapter(var context: Context , var data : ArrayList<data>) : RecyclerView.Adapter<adapter.viewHolder>() {
    class viewHolder(item : View) : RecyclerView.ViewHolder(item) {
        var namePDF = item.pdf_name_view
        var buttonDownload = item.downlodFile
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.show_pdf , parent , false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.namePDF.text = data[position].name
        holder.buttonDownload.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = data[position].uri
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
       return data.size
    }
}