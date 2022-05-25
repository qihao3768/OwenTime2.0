package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R
import com.example.owentime.bean.Tutorial
import com.example.owentime.databinding.LayoutTutorialBinding
import com.example.owentime.load

class TutorialListAdapter(private val detail: GotoDetail,val list: List<Tutorial>): RecyclerView.Adapter<TutorialListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_tutorial,parent,false)
        val binding= LayoutTutorialBinding.bind(view)
        return ViewHolder(detail,binding)
    }

    class ViewHolder(private val detail: GotoDetail,private val binding: LayoutTutorialBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Tutorial?){
            item?.run {
                binding.ivCover.load(cover)
                binding.ivCover.setOnClickListener {
                    detail.detail(id)
                }
            }

        }

    }

    interface GotoDetail{
        fun detail(cid:Int)
    }

}