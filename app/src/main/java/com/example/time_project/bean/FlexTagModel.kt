package com.example.time_project.bean

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.databinding.LayoutFlexTagBinding

class FlexTagModel(val tag: String):ItemBind{
    private var isSelected:Boolean=false
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= LayoutFlexTagBinding.bind(holder.itemView)
        binding.tvSpecification.text=tag
//        binding.tvSpecification.setOnClickListener {
//            binding.tvSpecification.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FE9520")).intoBackground()
//        }
    }
}