package com.example.owentime.bean

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.ItemProductBinding
import com.example.owentime.databinding.LayoutProjectBinding
import com.example.owentime.databinding.LayoutWorksBinding

/**
 * @description 类描述
 * @author yangjie
 * @date 2022-06-03
 */
data class GoodsModel(val title:String,val desc:String,val price:String,val count:String): ItemBind {
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemProductBinding.bind(holder.itemView)
        binding.tvProductTitle.text=title
        binding.tvProductPrice02.text=price
        binding.tvProductPtnum.text=count
        binding.tvProductDesc.text=desc
    }

}