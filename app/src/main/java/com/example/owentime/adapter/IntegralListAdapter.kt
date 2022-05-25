package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R
import com.example.owentime.bean.Integral
import com.example.owentime.databinding.LayoutIntegralBinding

class IntegralListAdapter: PagingDataAdapter<Integral, IntegralListAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Integral>() {
            override fun areItemsTheSame(oldItem: Integral, newItem: Integral): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Integral, newItem: Integral): Boolean {
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
            .inflate(R.layout.layout_integral,parent,false)
        val binding= LayoutIntegralBinding.bind(view)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: LayoutIntegralBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Integral?){
            item?.run {
                binding.integralUser.text=userName
                binding.integralYuanyin.text=reason
                binding.integralCount.text=coinCount.toString()
                binding.integralDesc.text=desc
            }

        }

    }

}