package com.example.time_project.web

//import com.tencent.smtt.sdk.WebSettings
//import com.tencent.smtt.sdk.WebView
//import com.tencent.smtt.sdk.WebViewClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityWebBinding


class WebActivity : BaseActivity(R.layout.activity_web) {
    private val mBinding by viewBinding(ActivityWebBinding::bind)
    override fun initData() {
        val settings: WebSettings = mBinding.web.settings
//        settings.cacheMode = WebSettings.LOAD_NO_CACHE
//        settings.javaScriptCanOpenWindowsAutomatically = false //设置js可以直接打开窗口，如window.open()，默认为false

        settings.javaScriptEnabled = true //是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞

        settings.useWideViewPort = true //设置此属性，可任意比例缩放。大视图模式

        settings.loadWithOverviewMode = true //和setUseWideViewPort(true)一起解决网页自适应问题

        mBinding.web.webViewClient=client

        mBinding.web.setBackgroundColor(getColor(R.color.white))

        val url=intent.getStringExtra("url")
        mBinding.web.loadUrl(url?:"")
    }

    private val client: WebViewClient = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(p0: WebView, p1: String) {
            super.onPageFinished(p0, p1)
            val javascript = "javascript:function ResizeImages() {" +
                    "var myimg,oldwidth;" +
                    "var maxwidth = document.body.clientWidth;" +
                    "for(i=0;i <document.images.length;i++){" +
                    "myimg = document.images[i];" +
                    "if(myimg.width > maxwidth){" +
                    "oldwidth = myimg.width;" +
                    "myimg.width = maxwidth;" +
                    "}" +
                    "}" +
                    "}"
//                String width = String.valueOf(AppUtils.getPhoneWidthPixels(BuyTryStudyActivity.this));
            //                String width = String.valueOf(AppUtils.getPhoneWidthPixels(BuyTryStudyActivity.this));
            p0.loadUrl(javascript)
            p0.loadUrl("javascript:ResizeImages();")

        }
    }






}