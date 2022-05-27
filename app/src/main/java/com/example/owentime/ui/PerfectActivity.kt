package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar

class PerfectActivity : BaseActivity(R.layout.activity_perfect) {
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.FE9520)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }
}