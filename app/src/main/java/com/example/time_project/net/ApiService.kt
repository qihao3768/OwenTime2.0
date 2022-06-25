package com.example.time_project.net

import com.example.time_project.base.BaseResponse
import com.example.time_project.bean.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("api/main/index")
    suspend fun banner():BaseResponse<HomeModel?>

//短信验证码
    @POST("api/sms/sendVer")
    @FormUrlEncoded
    suspend fun sendSms(@Field("phone") phone:String):BaseResponse<SmsModel?>

    @POST("api/user/smsLogin")
    @FormUrlEncoded
    suspend fun login(@Field("phone") phone:String,@Field("sms") sms:String,@Field("key_code") key_code:String):BaseResponse<LoginModel?>

//上传头像
    @POST("api/user/editUser")
    @Multipart
    suspend fun upload(@Part("token") token:RequestBody, @Part photo: MultipartBody.Part):BaseResponse<PhotoModel?>


    @POST("api/user/editUser")
    @FormUrlEncoded
    suspend fun uploadInfo(@Field("token") token:String, @Field("name") name:String, @Field("sex") sex:Int, @Field("birthday") birthday:String):BaseResponse<PhotoModel?>

    @POST("api/user/userInfo")
    @FormUrlEncoded
    suspend fun getUser(@Field("token") token: String):BaseResponse<User?>

//注销
    @POST("api/user/logout")
    suspend fun logOut():BaseResponse<String?>
//商品详情
    @POST("api/product/detail")
    @FormUrlEncoded
    suspend fun getDetail(@Field("code") code:String):BaseResponse<GoodsDetail?>


    //添加地址
    @POST("api/address/save")
    @FormUrlEncoded
    suspend fun saveAddress(@FieldMap map:HashMap<String,Any>):BaseResponse<String?>


    //确认订单
    @POST("api/order/confirmPage")
    @FormUrlEncoded
    suspend fun confirmPage(@FieldMap map:HashMap<String,Any>):BaseResponse<ConfirmOrderModel?>

    //确认订单
    @POST("api/address/change")
    @FormUrlEncoded
    suspend fun changeAddress(@FieldMap map:HashMap<String,Any>):BaseResponse<String?>

    //下单
    @POST("api/order/storage")
    suspend fun upOrder(@Body body:UpOrderRequestBody):BaseResponse<OrderSn?>

    //已购
    @POST("api/product/getUserProduct")
    suspend fun alreadyBuy():BaseResponse<AlreadyBuyModel?>

    //以后商品下的课程列表
    @POST("api/course/productCourse")
    @FormUrlEncoded
    suspend fun getCourse(@Field("product_id") product_id:String):BaseResponse<Course?>

    //保存课程播放记录
    @POST("api/course/storageRecord")
    @FormUrlEncoded
    suspend fun storageRecord(@Field("product_id") product_id:String,
                              @Field("courses_id") courses_id:String,
                              @Field("time") time:String):BaseResponse<String?>

    @POST("api/product/getUserProductlist")
    @FormUrlEncoded
    suspend fun pageYiGou(@Field("type") type:String,
                              @Field("page") page:String
    ):BaseResponse<YiGouPage?>



    companion object {
//        const val BASE_URL = "http://192.168.2.184:8080/"
        const val BASE_URL = "https://new.owentime.cn/"
    }
}