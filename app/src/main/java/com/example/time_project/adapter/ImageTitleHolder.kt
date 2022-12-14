package com.example.time_project.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.time_project.R

class ImageTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView
    var title: TextView

    init {
        imageView = view.findViewById(R.id.image)
        title = view.findViewById(R.id.bannerTitle)
    }
}