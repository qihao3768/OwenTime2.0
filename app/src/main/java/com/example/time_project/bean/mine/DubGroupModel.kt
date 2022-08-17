package com.example.time_project.bean.mine

import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemHover
import com.drake.brv.item.ItemPosition
import com.example.time_project.R

import com.example.time_project.databinding.ItemDubFirstBinding
import com.example.time_project.load


open class DubGroupModel:ItemExpand,ItemBind,ItemPosition, ItemHover {
    override var itemExpand: Boolean = false
    override var itemGroupPosition: Int=0
    override var itemSublist: List<Any?>?= mutableListOf<Dub>()
    override var itemPosition: Int=0
    override var itemHover: Boolean=true
    var name:String?=""
    var image:String?=""
    var time:String?=""
    var topname:String?=""
    var course:String?=""

//    var jsonSublist: List<Dub> = mutableListOf(GroupBasicModel(), GroupBasicModel(), GroupBasicModel(), GroupBasicModel())

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemDubFirstBinding.bind(holder.itemView)
        binding.ivWork.load(image)
        binding.tvWorksTime.text=time
        binding.tvWorksTitle.text=name
        binding.tvWorksTitleTop.text=topname
        if (itemExpand){
            binding.groupShow.text="收起"
            binding.ivShow.load(R.drawable.up_icon)
        }else{
            binding.groupShow.text="展开"
            binding.ivShow.load(R.drawable.down_icon)
        }
    }
}