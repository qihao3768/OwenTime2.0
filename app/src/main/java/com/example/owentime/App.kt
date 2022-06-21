package com.example.owentime

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.drake.statelayout.StateConfig
import com.tencent.mmkv.MMKV
import com.umeng.commonsdk.UMConfigure


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
        MMKV.defaultMMKV().encode("AUTH_SECRET","0ZxUI8VcqLd5hayQbWD5I8PAbw02jjSyK5Hv4ym7FJtxIx4fs1cqYDpJFf6Ehbm8upQOvAycOq1icx0Oo9XyQje37t+qcjujEn3jrsQM6YjGbQeoXfMGq88q5Sl0EJyRxN7+TMiyytbEliPuvjD9ZJkIfQkgubnqvkDn/vOUg1m1YPTMIuqAvo1u1UYhI8EYXBbK1mKFLEfIA4r1LreBFmmWgw8tSBSyszUgEIoTNKhweqKKuhgua4hLPvXzOwYBc3cLXQ2+AC+6kLxzVMPBflb4WOB0hthU4IiVdbcsJ+jNBdGD4O6n75IRz1HcjATb")

/*          *
           * 初始化common库
           * 参数1:上下文，不能为空
           * 参数2:【友盟+】 AppKey
           * 参数3:【友盟+】 Channel
           * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
           * 参数5:Push推送业务的secret*/
        //   UMConfigure.init(this, "6107697b864a9558e6d809d4", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.preInit(this, "6107697b864a9558e6d809d4", "Umeng")
        if (MMKV.defaultMMKV().decodeBool("splashDialog", false)) {
            val umInitConfig = UmInitConfig()
            umInitConfig.UMinit(this)
        }

        //初始化缺省页
        StateConfig.apply {
            emptyLayout=R.layout.empty_order
        }
//        CrashHandler.getInstance(this)


    }

}
