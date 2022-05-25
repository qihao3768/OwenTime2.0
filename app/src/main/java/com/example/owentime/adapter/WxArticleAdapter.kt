package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R

import com.example.owentime.bean.WxArticleDetail
import com.example.owentime.databinding.LayoutArticleBinding

class WxArticleAdapter(private val itemClickListener: ItemClickListener):PagingDataAdapter<WxArticleDetail,WxArticleAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<WxArticleDetail>() {
            override fun areItemsTheSame(oldItem: WxArticleDetail, newItem: WxArticleDetail): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: WxArticleDetail, newItem: WxArticleDetail): Boolean {
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
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_wxarticle,parent,false)
        val binding= LayoutArticleBinding.bind(view)
        return ViewHolder(itemClickListener,binding)
    }

    class ViewHolder(private val itemClickListener: ItemClickListener,private val binding:LayoutArticleBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:WxArticleDetail?){
            item?.run {
                binding.articleAuth.text=
                    author.ifBlank {
                        shareUser
                    }
                binding.articleTitle.text=title
                binding.articleChapter.text=superChapterName
                binding.articleTime.text=niceDate
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