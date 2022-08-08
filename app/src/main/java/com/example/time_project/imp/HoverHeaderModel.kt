package com.example.time_project.imp

import android.view.View
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.drake.brv.item.ItemHover
import com.example.time_project.databinding.LayoutHoverHeaderBinding
import com.tencent.mmkv.MMKV

/**
 * @description 类描述
 * @author yangjie
 * @date 2022-06-03
 */
class HoverHeaderModel(val header: String, val count: Int, val id: Int) : ItemHover,
    ItemBind {
    override var itemHover: Boolean = true

        val token= MMKV.defaultMMKV().decodeString("token")
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = LayoutHoverHeaderBinding.bind(holder.itemView)
        binding.tvHeader.text = header
        binding.tvSeemore.visibility = if (token.isNullOrBlank()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}