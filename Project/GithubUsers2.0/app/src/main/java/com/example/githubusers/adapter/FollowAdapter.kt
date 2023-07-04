package com.example.githubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.remote.response.FollowResponseItem
import com.example.githubusers.R

class FollowAdapter(private val listFollow: List<FollowResponseItem>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = listFollow[position].login
        Glide.with(holder.profile.context).load(listFollow[position].avatarUrl).into(holder.profile)

    }

    override fun getItemCount(): Int = listFollow.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val profile: ImageView = view.findViewById(R.id.profileUser)
    }

}