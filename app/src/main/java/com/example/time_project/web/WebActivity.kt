package com.example.time_project.web

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityWebBinding
import com.tencent.smtt.sdk.WebSettings

class WebActivity : BaseActivity(R.layout.activity_web) {
    private val mBinding by viewBinding(ActivityWebBinding::bind)
    override fun initData() {
        val url=intent.getStringExtra("url")
       mBinding.web.loadUrl(url?:"")


        val settings: WebSettings = mBinding.web.settings
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptCanOpenWindowsAutomatically = false //设置js可以直接打开窗口，如window.open()，默认为false

        settings.javaScriptEnabled = true //是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞

        settings.loadWithOverviewMode = true
        settings.setSupportZoom(true) //是否可以缩放，默认true

        settings.builtInZoomControls = true //是否显示缩放按钮，默认false

        settings.useWideViewPort = true //设置此属性，可任意比例缩放。大视图模式

        settings.loadWithOverviewMode = true //和setUseWideViewPort(true)一起解决网页自适应问题

        settings.setAppCacheEnabled(false) //是否使用缓存

        settings.domStorageEnabled = true //开启本地DOM存储

        settings.loadsImagesAutomatically = true // 加载图片

        settings.mediaPlaybackRequiresUserGesture = false //播放音频，多媒体需要用户手动？设置为false为可自动播放

        // 加入自动缩放
        // 加入自动缩放
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.loadWithOverviewMode = true
    }
}