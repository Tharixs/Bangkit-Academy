package com.example.githubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.remote.response.Items
import com.example.githubusers.R


class UsersAdapter(private val listUsers: List<Items>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvUsername.text = listUsers[position].login
        Glide.with(holder.profile.context).load(listUsers[position].avatarUrl).into(holder.profile)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listUsers[holder.adapterPosition].login,
                listUsers[holder.adapterPosition].avatarUrl
            )
        }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val profile: ImageView = view.findViewById(R.id.profileUser)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String, avatar: String)
    }

}