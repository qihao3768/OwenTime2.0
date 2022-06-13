package com.example.owentime.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.owentime.R
import com.example.owentime.toast
import com.jeremyliao.liveeventbus.LiveEventBus

abstract class BaseActivity(@LayoutRes private val layoutId:Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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