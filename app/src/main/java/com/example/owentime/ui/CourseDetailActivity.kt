package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.CourseItemModel
import com.example.owentime.bean.GoodsModel
import com.example.owentime.databinding.ActivityCourseDetailBinding
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.imp.HoverHeaderModel
import com.example.owentime.start
import com.gyf.immersionbar.ktx.immersionBar

class CourseDetailActivity : BaseActivity(R.layout.activity_course_detail) {
    private val mBinding by viewBinding (ActivityCourseDetailBinding::bind)
    private val testString="动物妈妈们在河边的草丛里发现了一颗蛋，动物妈妈们在河边的草丛里发现了一颗蛋xxxxxxxxxxxxxxxxxxxxxxxxx"
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.listCourse.linear().setup {
            addType<CourseItemModel>(R.layout.item_course)
            models=getData()
            onClick(R.id.continue_play){
                val model=getModel<CourseItemModel>(modelPosition)
                val title=model.title
                val url=model.url
                val map= HashMap<String,String>()
                map["title"] = title
                map["url"] = url
                start(this@CourseDetailActivity,ExoplayerActivity().javaClass,map)
            }
            setAnimation(AnimationType.SLIDE_BOTTOM)
        }
//        mBinding.layoutShow.setOnClickListener {
//            mBinding.courseJianjie.maxLines=4
//            mBinding.courseJianjie.text=testString
//        }
    }
    private fun getData():List<CourseItemModel>{
        return  listOf(

            CourseItemModel("","第一集 看图说话","02:25",""),
            CourseItemModel("","第二集 阅读分享","02:25",""),
            CourseItemModel("","第三集 审辩思维","02:25",""),
            CourseItemModel("","第四集 绘声绘色","02:25",""),
        )
    }
}