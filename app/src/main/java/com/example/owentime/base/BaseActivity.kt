package com.example.owentime.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.owentime.R

abstract class BaseActivity(@LayoutRes private val layoutId:Int) : AppCompatActivity(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when(layoutId){
            R.layout.activity_exoplayer->{
//                requestWindowFeature(Window.FEATURE_NO_TITLE)
            }
        }

        setContentView(layoutId)
        initData()
    }

    abstract fun initData()
}