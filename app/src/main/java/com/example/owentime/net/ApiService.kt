package com.example.owentime.net

import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("/api/main/index")
    suspend fun banner():BaseResponse<HomeModel>

//短信验证码
    @POST("/api/sms/sendVer")
    @FormUrlEncoded
    suspend fun sendSms(@Field("phone") phone:String):BaseResponse<SmsModel>

    @POST("/api/user/smsLogin")
    @FormUrlEncoded
    suspend fun login(@Field("phone") phone:String,@Field("sms") sms:String,@Field("key_code") key_code:String):BaseResponse<LoginModel>

//上传头像
    @POST("/api/user/editUser")
    @Multipart
    suspend fun upload(@Part("token") token:RequestBody, @Part file: MultipartBody.Part):BaseResponse<PhotoModel>

    companion object {
//        const val BASE_URL = "https://wanandroid.com/"
        const val BASE_URL = "http://192.168.2.184:8080"
    }
}