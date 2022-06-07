package com.example.owentime.bean

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.ItemCourseBinding
import com.example.owentime.databinding.LayoutHoverHeaderBinding

data class CourseItemModel(val pic:String,val title:String,val time:String,val url:String):ItemBind{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemCourseBinding.bind(holder.itemView)
        binding.ivTimer.text=time
        binding.tvPlayingTitle.text=title
    }
}