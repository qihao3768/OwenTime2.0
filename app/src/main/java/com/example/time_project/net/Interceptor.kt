package com.example.time_project.net

import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class Interceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token :String?=MMKV.defaultMMKV().decodeString("token","")
        Log.d("token",token.toString())
        val original:Request = chain.request()
        val request:Request=original.newBuilder()
            .header("Authorization", "Bearer $token"?:"")
            .build()
        chain.withReadTimeout(10,TimeUnit.SECONDS)
        chain.withReadTimeout(10,TimeUnit.SECONDS)
        chain.withConnectTimeout(10,TimeUnit.SECONDS)
        return chain.proceed(request)
    }
}