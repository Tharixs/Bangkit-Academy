package com.example.storyapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.view.network.DetailResponse

class DetailAdapter : PagingDataAdapter<DetailResponse, DetailAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    data.name,
                    data.photoUrl,
                    data.id
                )
            }
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailResponse) {
            binding.tvNameUser.text = data.name
            Glide.with(binding.ivStory.context).load(data.photoUrl)
                .into(binding.ivStory)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailResponse,
                newItem: DetailResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailResponse,
                newItem: DetailResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(name: String, avatar: String, id: String)
    }
}