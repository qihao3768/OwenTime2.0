package com.example.time_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar

class CouponActivity : BaseActivity(R.layout.activity_coupon) {
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(false)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }
}