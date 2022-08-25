package com.example.imageconvert.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageconvert.R
import com.example.imageconvert.model.repository.OnItemClick

class AdapterPhoto(
    private val onItemClick: OnItemClick
    ) : RecyclerView.Adapter<AdapterPhoto.PhotoViewHolder>() {

    private val data = ArrayList<String>()
    fun setData(listOfPhotos: List<String>) {
        data.clear()
        data.addAll(listOfPhotos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AdapterPhoto.PhotoViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(item)
    }

    override fun onBindViewHolder(holder: AdapterPhoto.PhotoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PhotoViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        private val image = item.findViewById<ImageView>(R.id.image)
        fun bind(stringPath: String) {
            Glide.with(item.context).load(stringPath).into(image)
            item.setOnClickListener {
                onItemClick.onImageItemClick(stringPath)
            }
        }
    }
}