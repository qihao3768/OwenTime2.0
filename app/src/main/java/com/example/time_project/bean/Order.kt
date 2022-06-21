package com.example.time_project.bean

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.databinding.ItemOrderBinding

data class OrderModel(val pic:String,val title:String,val price:String,val state:String):ItemBind{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemOrderBinding.bind(holder.itemView)
        binding.orderTitle.text=title
//        binding.orderPic.load(pic)
        binding.orderPrice.text=price
        binding.orderState.text=state

    }
}