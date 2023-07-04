package com.example.mafilmapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mafilmapp.databinding.ItemRowHeroBinding

class ListHeroAdapter(
    private val listHero: ArrayList<Hero>
) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, deskription, photo) = listHero[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.binding.imageView)
        holder.binding.tvTitle.text = name
        holder.binding.tvDesk.text = deskription

//        holder.itemView.setOnClickListener{
//            Toast.makeText(holder.itemView.context, "Kamu memilih ${listHero[holder.adapterPosition].name}", Toast.LENGTH_SHORT).show()
//        }
        holder.itemView.setOnClickListener { (onItemClickCallback.onItemClicked(listHero[holder.adapterPosition])) }
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

}