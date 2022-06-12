package com.example.owentime

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.drake.statelayout.StateConfig
import com.example.owentime.util.CrashHandler
import com.tencent.mmkv.MMKV


/**
 * 0、全局的applicationContext
 * 1、缓存数据（用户信息，token，头像昵称等等）
 * 2、传递数据（activity、fragment数据的传递，传递后记得清空）
 * 3、初始化第三方SDK：1日志框架、2内存溢出检测  等等
 */


class App : Application() {
    //val hashMap=hashMapof()
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        fun getContext(): Context? {
            return context
        }
    }



    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val rootDir=MMKV.initialize(this)
        //初始化第三方SDK：1日志框架、2内存溢出检测  等等

        //初始化缺省页
        StateConfig.apply {
            emptyLayout=R.layout.empty_order
        }
//        CrashHandler.getInstance(this)


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        xcrash.XCrash.init(this)
    }
}
