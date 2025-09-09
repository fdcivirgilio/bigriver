package com.example.bigriver.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File
import com.example.bigriver.R

class ImageAdapter(private var images: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val filePath = images[position]
        Glide.with(holder.imageView.context)
            .load(File(filePath))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size

    fun updateData(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }
}