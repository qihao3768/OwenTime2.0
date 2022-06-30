package com.example.time_project.base

import android.os.Bundle
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.time_project.ActivityManager
import com.example.time_project.R
import com.example.time_project.toast
import com.jeremyliao.liveeventbus.LiveEventBus

abstract class BaseActivity(@LayoutRes private val layoutId:Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.instance.addActivity(this)
        when(layoutId){
            R.layout.activity_exoplayer->{
                requestWindowFeature(Window.FEATURE_NO_TITLE)
            }
        }

        setContentView(layoutId)
        initData()

        LiveEventBus.get<String>("message").observe(this, Observer {
            toast(it)
        })
    }

    abstract fun initData()
}