package com.example.time_project.util

import android.view.View
import java.util.*

abstract class NoDoubleClickListener :View.OnClickListener{
    val MIN_CLICK_DELAY_TIME = 1000 //这里设置不能超过多长时间


    private var lastClickTime: Long = 0

    protected abstract fun onNoDoubleClick(v: View)

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }
}