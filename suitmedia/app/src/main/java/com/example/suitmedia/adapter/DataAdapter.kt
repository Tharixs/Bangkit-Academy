package com.example.suitmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia.R
import com.example.suitmedia.database.UserDatabase
import com.example.suitmedia.response.DataItem

class DataAdapter(private val dataItem: List<DataItem>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false))


    override fun getItemCount(): Int = dataItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = dataItem[position].firstName + " " + dataItem[position].lastName
        holder.email.text = dataItem[position].email
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.tv_name)
        val email = view.findViewById<TextView>(R.id.tv_email)
        val img = view.findViewById<View>(R.id.image_profile)

    }
}