package com.example.time_project.base

import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.time_project.ActivityManager
import com.example.time_project.R
import com.example.time_project.toast
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxj.xpopup.widget.LoadingView
import razerdp.basepopup.BasePopupWindow

abstract class BaseActivity(@LayoutRes private val layoutId:Int) : AppCompatActivity() {
    lateinit var popupWindow: BasePopupWindow
    lateinit var loading:LoadingView
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
        popupWindow = BasePopWindow(this)
    }

    abstract fun initData()


    protected fun showLoading(){
        val view = popupWindow.createPopupById(R.layout.layout_loading)
        loading=view.findViewById<LoadingView>(R.id.loading)
        popupWindow.contentView = view
        popupWindow.setBackPressEnable(false)
        popupWindow.popupGravity = Gravity.CENTER
        popupWindow.isOutSideTouchable = false
        popupWindow.setOutSideDismiss(false)
        popupWindow.showPopupWindow()
    }

    protected fun hideLoading(){
        popupWindow.run {
            this.dismiss()
        }
    }

    protected fun removeActivity(){
        ActivityManager.instance.removeActivity(this)
    }
}