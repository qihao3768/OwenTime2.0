package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.PhotoModel
import com.example.owentime.bean.User
import com.example.owentime.bean.UserModel
import com.example.owentime.net.RetrofitClient
import com.example.owentime.util.RequestFileUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserInfoRepo :BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun upload(token:String,path:String): BaseResponse<PhotoModel> = request {
        val mtoken = RequestBody.create(null, token)
        val file = File(path)
        val requestBody: MultipartBody.Part = RequestFileUtil.uploadFile("photo", file)
        mService.upload(mtoken,requestBody)
    }

    suspend fun uploadInfo(token:String,name:String,sex:Int,birth:String): BaseResponse<PhotoModel> = request {
        mService.uploadInfo(token,name,sex,birth)
    }
}