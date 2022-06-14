package com.example.owentime.net

import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.*
import retrofit2.http.*

interface ApiService {
    @POST("api/main/index")
    suspend fun banner():BaseResponse<HomeModel>

//短信验证码
    @POST("/api/sms/sendVer")
    @FormUrlEncoded
    suspend fun sendSms(@Field("phone") phone:String):BaseResponse<SmsModel>



    companion object {
//        const val BASE_URL = "https://wanandroid.com/"
        const val BASE_URL = "http://192.168.2.184:8080"
    }
}