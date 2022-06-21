package com.example.time_project

import android.content.Context
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

class UmInitConfig {
    fun UMinit(context: Context?) {
        UMConfigure.init(
            context,
            "6107697b864a9558e6d809d4",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        PlatformConfig.setWeixin("wx58dff157a444095f", "936a2a3674c235ee406bee1f7ab377f7")
        PlatformConfig.setWXFileProvider("com.example.time_project.fileprovider")
    }
}