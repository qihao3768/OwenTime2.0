package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R
import com.example.owentime.bean.ProjectDetail
import com.example.owentime.databinding.LayoutProjectBinding
import com.example.owentime.load

class ProjectListAdapter(private val itemClickListener: ItemClickListener): PagingDataAdapter<ProjectDetail, ProjectListAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProjectDetail>() {
            override fun areItemsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_project,parent,false)
        val binding= LayoutProjectBinding.bind(view)
        return ViewHolder(itemClickListener,binding)
    }

    class ViewHolder(private val itemClickListener: ItemClickListener, private val binding: LayoutProjectBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProjectDetail?){
            item?.run {
                binding.articleAuth.text=
                    author.ifBlank {
                        shareUser
                    }
                binding.articleTitle.text=title
                binding.articleChapter.text=superChapterName
                binding.articleTime.text=niceDate
                binding.projectImg.load(envelopePic)
                binding.root.setOnClickListener {
                    itemClickListener.click(link)
                }
            }

        }

    }

    interface ItemClickListener{
        fun click(url:String)
    }
}