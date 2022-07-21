package com.example.time_project.ui

import android.content.Intent
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.other.WorksBean
import com.example.time_project.databinding.ActivityWorksBinding
import com.example.time_project.fastClick
import com.example.time_project.start
import com.example.time_project.util.IntentExtraString
import com.gyf.immersionbar.ktx.immersionBar

/***
 * 我的作品
 */
class WorksActivity : BaseActivity(R.layout.activity_works) {
    private val mBinding by viewBinding(ActivityWorksBinding::bind)
    companion object IntentOptions{
        var Intent.url by IntentExtraString("url")
    }
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        val data=getData()
        mBinding.rvWorks.linear().setup {
            addType<WorksBean>(R.layout.layout_works)
            onFastClick(R.id.btn_play){
                intent.url="https://owen-time-test.oss-cn-beijing.aliyuncs.com/courses/cou/1643348728_216a94a44ba39a71.mp4"
                start(this@WorksActivity,ExoplayerActivity().javaClass,intent)
            }
        }.models=data


        mBinding.titleWorks.leftView.fastClick {
            finish()
        }
    }

    private fun getData():MutableList<Any>{

        return mutableListOf<Any>().apply {

            for (i in 0..2) add(WorksBean("","绘声绘色","2022-01-01 18:00"))
        }
    }
}