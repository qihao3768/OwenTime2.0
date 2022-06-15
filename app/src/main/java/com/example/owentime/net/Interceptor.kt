package com.example.owentime.net

import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val original:Request = chain.request()
        val request:Request=original.newBuilder()
//            .header("Authorization","Bearer "+MMKV.defaultMMKV().decodeString("token")?:"")
            .build()
        return chain.proceed(request)
    }
}