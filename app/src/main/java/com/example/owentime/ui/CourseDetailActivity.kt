package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.CourseItemModel
import com.example.owentime.bean.GoodsModel
import com.example.owentime.databinding.ActivityCourseDetailBinding
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.imp.HoverHeaderModel

class CourseDetailActivity : BaseActivity(R.layout.activity_course_detail) {
    private val mBinding by viewBinding (ActivityCourseDetailBinding::bind)
    override fun initData() {
        mBinding.listCourse.linear().setup {
            addType<CourseItemModel>(R.layout.item_course)
            models=getData()
        }
    }
    private fun getData():List<CourseItemModel>{
        return  listOf(

            CourseItemModel("","第一集 看图说话","02:25"),
            CourseItemModel("","第二集 阅读分享","02:25"),
            CourseItemModel("","第三集 审辩思维","02:25"),
            CourseItemModel("","第四集 绘声绘色","02:25"),
        )
    }
}