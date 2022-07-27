package com.example.time_project.bean.mine
import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.drake.brv.item.ItemExpand
import com.example.time_project.databinding.ItemDubFirstBinding
import com.example.time_project.databinding.LayoutWorksBinding
import com.example.time_project.load
import com.google.gson.annotations.SerializedName


data class DubListModel(
    @SerializedName("dub")
    var dub: List<Dub?>? = listOf(),
    @SerializedName("dub_course")
    val dubCourse: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = "",

)

data class Dub(
    @SerializedName("course_id")
    val courseId: Int? = 0,
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("url")
    val url: String? = "",

    var image: String? = "",

    var name: String? = "",
):ItemBind,ItemExpand{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= LayoutWorksBinding.bind(holder.itemView)
        binding.tvWorksTitle.text=name
        binding.tvWorksTime.text=createdAt
        binding.ivWork.load(image)
    }

    override var itemExpand: Boolean=false

    override var itemGroupPosition: Int=0

    override var itemSublist: List<Any?>?=null

}