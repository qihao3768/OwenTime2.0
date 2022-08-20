package com.example.time_project.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityFeedBackBinding
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV

class FeedBackActivity : BaseActivity(R.layout.activity_feed_back) {
    private val mBinding by viewBinding(ActivityFeedBackBinding::bind)
    private val viewModel by viewModels<OwenViewModel>()
    private val mmkv:MMKV = MMKV.defaultMMKV()
    override fun initData() {

        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.feedTitle.fastClick {
            finish()
        }
        mBinding.btnFeed.checkLogin(this,object :TodoListener{
            override fun todo() {
                val content: String = mBinding.edtFeed.text.toString() ?: ""
                val token = mmkv.decodeString("token")?:""
             if (content.isNullOrBlank()){
                 toast("请填写反馈内容")
             }else{
                 viewModel.feedback(token,content).observe(this@FeedBackActivity, Observer {
                     if (it.code==1000){
                         toast("反馈成功")
                         finish()
                     }
                 })
             }

            }

        })


    }
}