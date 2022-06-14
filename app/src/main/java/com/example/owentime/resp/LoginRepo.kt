package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.Register
import com.example.owentime.bean.SmsModel
import com.example.owentime.net.RetrofitClient

class LoginRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
//    suspend fun login(username:String,password:String): BaseResponse<Register> = request {
//        mService.login(username,password)
//    }

    suspend fun sms(phone:String): BaseResponse<SmsModel> = request {
        mService.sendSms(phone)
    }
}

