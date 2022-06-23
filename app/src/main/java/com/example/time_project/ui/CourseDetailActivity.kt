package com.example.time_project.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.Course
import com.example.time_project.bean.CourseItemModel
import com.example.time_project.bean.CourseX
import com.example.time_project.databinding.ActivityCourseDetailBinding
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.vm.LoginViewModel
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar

class CourseDetailActivity : BaseActivity(R.layout.activity_course_detail) {
    private val mBinding by viewBinding (ActivityCourseDetailBinding::bind)
    private val testString="动物妈妈们在河边的草丛里发现了一颗蛋，动物妈妈们在河边的草丛里发现了一颗蛋xxxxxxxxxxxxxxxxxxxxxxxxx"

    private val mViewModel by viewModels<OwenViewModel>()

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }

        getData()
        mBinding.detailTitle.leftView.fastClick {
            finish()
        }
    }

    /***
     * 获取课程列表
     */
    private fun getData(){
        mViewModel.getCourse(intent.iproductId.toString()).observe(
            this, Observer {
                it?.run {
                    when(code){
                        1000->{
                            val body:Course?=data
                            if (body==null){
                                show(null)
                            }else{
                                mBinding.detailTitle.title=body.name?:""
                                mBinding.courseJianjie.text=body.introduction?:""
                                mBinding.ivCoursePic.load(body.imgHead?:"")
                                show(body.course)
                            }

                        }
                        401->{
                            toast("登录状态失效，请重新登录")
                            show(null)
                        }else->{
                        toast(message.toString())
                        show(null)
                        }
                    }
                }
            }
        )
    }

    /***
     * 展示课程列表
     */
    private fun show(data:List<CourseX>?){
        if (data.isNullOrEmpty()){
            //显示空视图
            mBinding.stateCourse.apply {
                emptyLayout=R.layout.empty_order
            }.showEmpty()
        }else{
            mBinding.listCourse.linear().setup {
                addType<CourseX>(R.layout.item_course)
                models=data
                onClick(R.id.continue_play){
                    val model=getModel<CourseX>(modelPosition)
                    if (model.isLocked==1){
                        toast("请先观看上一节视频，完成解锁")
                    }else{
                        intent.courseTitle=model.name?:""
                        intent.courseUrl=model.url?:""
                        start(this@CourseDetailActivity,ExoplayerActivity().javaClass,intent)
                    }

                }
                setAnimation(AnimationType.SLIDE_BOTTOM)
            }
        }

    }

}