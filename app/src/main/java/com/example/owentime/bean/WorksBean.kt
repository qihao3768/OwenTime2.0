package com.example.owentime.bean

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.LayoutWorksBinding

data class WorksBean(var pic:String,var name:String,var time:String):ItemBind{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding=LayoutWorksBinding.bind(holder.itemView)
        binding.tvWorksTitle.text=name
    }
}