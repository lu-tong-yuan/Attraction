package com.tommy.attractions.features.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tommy.attractions.R
import com.tommy.attractions.features.main.model.Image

class MyPagerAdapter(private val images: List<Image>) : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        if (images.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(images[position].src)
                .placeholder(R.drawable.default_picture)
                .into(holder.photoImage)
        } else {
            holder.photoImage.setImageResource(R.drawable.default_picture)
        }
    }

    override fun getItemCount(): Int {
        return if (images.isEmpty()) 1 else images.size
    }


}

class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var photoImage : ImageView = view.findViewById(R.id.ivPhoto)
}