package com.example.time_project.net

import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token :String?=MMKV.defaultMMKV().decodeString("token","")
        Log.d("token",token.toString())
        val original:Request = chain.request()
        val request:Request=original.newBuilder()
            .header("Authorization", "Bearer $token"?:"")
            .build()
        return chain.proceed(request)
    }
}