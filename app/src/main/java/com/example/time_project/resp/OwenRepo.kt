package com.example.time_project.resp

import com.example.time_project.base.BaseRepository
import com.example.time_project.base.BaseResponse
import com.example.time_project.bean.*
import com.example.time_project.net.RetrofitClient
import com.example.time_project.util.RequestFileUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class OwenRepo():BaseRepository(){

    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun banner():BaseResponse<HomeModel?> = request {
        mService.banner()
    }

    /***
     * 订单详情
     */
    suspend fun detail(code:String):BaseResponse<GoodsDetail?> = request {
        mService.getDetail(code)
    }

    suspend fun sms(phone:String): BaseResponse<SmsModel?> = request {
        mService.sendSms(phone)
    }

    suspend fun login(phone:String,sms:String,key:String): BaseResponse<LoginModel?> = request {
        mService.login(phone,sms,key)
    }

    /***
     * 上传头像
     */
    suspend fun upload(token:String,path:String): BaseResponse<PhotoModel?> = request {
        val mtoken = RequestBody.create(null, token)
        val file = File(path)
        val requestBody: MultipartBody.Part = RequestFileUtil.uploadFile("photo", file)
        mService.upload(mtoken,requestBody)
    }

    /***
     * 上传用户信息
     */
    suspend fun uploadInfo(token:String,name:String,sex:Int,birth:String): BaseResponse<PhotoModel?> = request {
        mService.uploadInfo(token,name,sex,birth)
    }

    /***
     * 获取用户信息
     */
    suspend fun getUser(token:String): BaseResponse<User?> = request {
        mService.getUser(token)
    }

    /***
     * 退出登录
     */
    suspend fun logOut(): BaseResponse<String?> = request {
        mService.logOut()
    }

    /***
     * 添加地址
     */
    suspend fun saveAddress(map:HashMap<String,Any>): BaseResponse<String?> = request {
        mService.saveAddress(map)
    }

    /***
     * 确认订单
     */
    suspend fun confirmPage(map:HashMap<String,Any>): BaseResponse<ConfirmOrderModel?> = request {
        mService.confirmPage(map)
    }

    /***
     * 修改地址
     */
    suspend fun changeAddress(map:HashMap<String,Any>): BaseResponse<String?> = request {
        mService.changeAddress(map)
    }

    /***
     * 下单
     */
    suspend fun upOrder(body:UpOrderRequestBody): BaseResponse<OrderSn?> = request {
        mService.upOrder(body)
    }

    /***
     * 已购
     */
    suspend fun alreadyBuy(): BaseResponse<AlreadyBuyModel?> = request {
        mService.alreadyBuy()
    }
}
