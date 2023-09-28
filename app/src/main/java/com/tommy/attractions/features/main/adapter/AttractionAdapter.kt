package com.tommy.attractions.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.tommy.attractions.R
import com.tommy.attractions.databinding.RowAttractionBinding
import com.tommy.attractions.features.main.OnItemClickListener
import com.tommy.attractions.features.main.model.Attraction

class AttractionAdapter(private val itemClickListener: OnItemClickListener, var attractions : List<Attraction>) : Adapter<AttractionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding = RowAttractionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AttractionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(attractions[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(attractions[position])
        }
    }
}

class AttractionViewHolder(private val binding: RowAttractionBinding) : ViewHolder(binding.root) {
    fun bind(data : Attraction) {
        binding.tvTitle.text = data.name
        binding.tvDes.text = data.introduction
        if (data.images.isNotEmpty()) {
            Glide.with(binding.root)
                .load(data.images[0].src)
                .placeholder(R.drawable.default_picture)
                .into(binding.ivPhoto)
        } else {
            binding.ivPhoto.setImageResource(R.drawable.default_picture)
        }
    }
}