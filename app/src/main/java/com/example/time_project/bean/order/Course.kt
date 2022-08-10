package com.example.time_project.bean.order
import android.view.View
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.databinding.ItemCourseBinding
import com.example.time_project.databinding.LayoutWorksBinding
import com.example.time_project.load
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/***
 * 课程详情
 */
data class Course(
    @SerializedName("course")
    val course: List<CourseX> = listOf(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("img_head")
    val imgHead: String? = "",
    @SerializedName("introduction")
    val introduction: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("tags")
    val tags: String? = "",
    @SerializedName("punch_status")
    val punchStatus: Int? = 0
):Serializable

data class CourseX(
    @SerializedName("dub_course")
    val dubCourse: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("is_locked")
    val isLocked: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("product_id")
    val productId: Int? = 0,
    @SerializedName("sort")
    val sort: Int? = 0,
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("duration")
    val duration: String? = ""
):ItemBind,Serializable{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemCourseBinding.bind(holder.itemView)
        binding.ivPlayingCourse.load(image?:"")//图像
        binding.tvPlayingTitle.text=name?:""//名字
        binding.tvPlayingTime.text=duration?:""//时长
        binding.ivSuo.visibility=when(isLocked){
            0->{
                View.GONE
            }
            else->{
                View.VISIBLE
            }
        }
    }
}