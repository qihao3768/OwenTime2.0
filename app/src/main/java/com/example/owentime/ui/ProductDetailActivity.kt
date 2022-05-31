package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar

class ProductDetailActivity : BaseActivity(R.layout.activity_product_detail) {


    override fun initData() {
        immersionBar {
            statusBarColor(R.color.FE9520)
        }
    }
}