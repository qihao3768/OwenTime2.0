package com.example.owentime.bean

import android.graphics.Color
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.LayoutFlexTagBinding
import com.example.owentime.databinding.LayoutWorksBinding

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