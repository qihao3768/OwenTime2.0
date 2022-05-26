package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R

import com.example.owentime.bean.ArticleData
import com.example.owentime.bean.GoodsBean
import com.example.owentime.checkLogin
import com.example.owentime.databinding.ItemProductBinding
import com.example.owentime.databinding.LayoutArticleBinding

class ArticleAdapter(private val itemClickListener: ItemClickListener):PagingDataAdapter<GoodsBean,ArticleAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GoodsBean>() {
            override fun areItemsTheSame(oldItem: GoodsBean, newItem: GoodsBean): Boolean {
                return oldItem.goodsId == newItem.goodsId
            }

            override fun areContentsTheSame(oldItem: GoodsBean, newItem: GoodsBean): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product,parent,false)
        val binding= ItemProductBinding.bind(view)
        return ViewHolder(itemClickListener,binding)
    }

    class ViewHolder(private val itemClickListener: ItemClickListener,private val binding:ItemProductBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:GoodsBean?){
            item?.run {
//                settext
            }

        }
    }
    interface ItemClickListener{
        fun click(url:String)
    }
}