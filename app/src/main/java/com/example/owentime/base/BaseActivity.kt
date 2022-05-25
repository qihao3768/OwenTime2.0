package com.example.owentime.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class BaseActivity(@LayoutRes private val layoutId:Int) : AppCompatActivity(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (layoutId== R.layout.activity_splash){
//            installSplashScreen()
//        }
        setContentView(layoutId)
        initData()
    }

    abstract fun initData()
}