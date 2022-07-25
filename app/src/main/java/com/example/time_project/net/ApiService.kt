package com.example.time_project.net

import com.example.time_project.base.BaseResponse
import com.example.time_project.bean.*
import com.example.time_project.bean.home.HomeModel
import com.example.time_project.bean.home.User
import com.example.time_project.bean.login.LoginModel
import com.example.time_project.bean.login.PhotoModel
import com.example.time_project.bean.login.SmsModel
import com.example.time_project.bean.order.*
import com.example.time_project.bean.yigou.AlreadyBuyModel
import com.example.time_project.bean.yigou.YiGouPage
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

//退出登录
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

    //修改地址
    @POST("api/address/change")
    @FormUrlEncoded
    suspend fun changeAddress(@FieldMap map:HashMap<String,Any>):BaseResponse<String?>

    //下单
    @POST("api/order/storage")
    suspend fun upOrder(@Body body:UpOrderRequestBody):BaseResponse<OrderSn?>

    //已购
    @POST("api/product/getUserProduct")
    @FormUrlEncoded
    suspend fun alreadyBuy(@Field("page") page: String):BaseResponse<AlreadyBuyModel?>
    //已购分页
//    @POST("api/product/getUserProduct")
//    suspend fun alreadyBuy(@Field("page") page: String):BaseResponse<AlreadyBuyModel?>

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

    //已购-分页
    @POST("api/product/getUserProductlist")
    @FormUrlEncoded
    suspend fun pageYiGou(@Field("type") type:String,
                              @Field("page") page:String
    ):BaseResponse<YiGouPage?>


    @POST("api/pay/wechatApp")
    @FormUrlEncoded
    suspend fun wechatPay(@Field("type") type:String,
                          @Field("page") page:String
    ):BaseResponse<YiGouPage?>

    //课程打卡
    @POST("api/course/doPunch")
    @FormUrlEncoded
    suspend fun doPunch(@Field("product_id") product_id:String,
                          @Field("courses_id") courses_id:String
    ):BaseResponse<String?>

    //微信支付
    @POST("api/pay/wechatApp")
    @FormUrlEncoded
    suspend fun weiChatPay(@Field("amount") amount:String,
                           @Field("subject") subject:String,
                           @Field("order") order:String
    ):BaseResponse<WeiXinPay?>

    //支付宝支付
    @POST("api/pay/aliApp")
    @FormUrlEncoded
    suspend fun aliPay(@Field("amount") amount:String,
                           @Field("subject") subject:String,
                           @Field("order") order:String
    ):BaseResponse<String?>

//注销
    @POST("api/user/logOff")
    suspend fun logOff():BaseResponse<String?>
//订单列表
    @POST("api/order/list")
    @FormUrlEncoded
    suspend fun orderList(@Field("status") status:String,@Field("page") page: String):BaseResponse<OrderListModel?>

//    @POST("api/order/list")
//    @FormUrlEncoded
//    suspend fun orderListPage(@Field("status") status:String,@Field("page") page: String):OrderListModel

    //保存配音
    @POST("api/dub/storageDub")
    @Multipart
    suspend fun storageDub(@Part("course_id") course_id:RequestBody,@Part url: MultipartBody.Part):BaseResponse<EmptyModel?>

    companion object {
//        const val BASE_URL = "http://192.168.2.184:8080/"
        const val BASE_URL = "https://new.owentime.cn/"
    }
}