package com.example.storyapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.view.main.DetileActivity
import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.response.ListStoryItem

class StoryAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = listStory[position].name
        Glide.with(holder.storyImage.context).load(listStory[position].photoUrl)
            .into(holder.storyImage)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listStory[holder.adapterPosition].name,
                listStory[holder.adapterPosition].photoUrl,
                listStory[holder.adapterPosition].id
            )
        }
    }

    override fun getItemCount() = listStory.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvNameUser)
        val storyImage: ImageView = view.findViewById(R.id.ivStory)
    }

    interface OnItemClickCallback {
        fun onItemClicked(name: String, avatar: String, id:String)
    }


}