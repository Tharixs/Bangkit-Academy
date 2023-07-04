package com.example.githubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.local.entity.Favorite


class FavoriteAdapter(private val listFavorite: List<Favorite>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = listFavorite[position].username
        Glide.with(holder.profile.context).load(listFavorite[position].avatar)
            .into(holder.profile)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listFavorite[holder.adapterPosition].username,
                listFavorite[holder.adapterPosition].avatar
            )
        }
    }

    override fun getItemCount(): Int = listFavorite.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val profile: ImageView = view.findViewById(R.id.profileUser)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String, avatar: String?)
    }

}