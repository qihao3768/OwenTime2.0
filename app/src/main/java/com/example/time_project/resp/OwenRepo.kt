package com.example.time_project.resp

//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
import com.example.time_project.base.BaseRepository
import com.example.time_project.base.BaseResponse
import com.example.time_project.bean.*
import com.example.time_project.bean.home.HomeModel
import com.example.time_project.bean.home.User
import com.example.time_project.bean.login.LoginModel
import com.example.time_project.bean.login.PhotoModel
import com.example.time_project.bean.login.SmsModel
import com.example.time_project.bean.mine.DubListModel
import com.example.time_project.bean.order.*
import com.example.time_project.bean.yigou.AlreadyBuyModel
import com.example.time_project.bean.yigou.YiGouPage
import com.example.time_project.net.RetrofitClient
//import com.example.time_project.paging.OrderListPagingSource
import com.example.time_project.util.RequestFileUtil
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class OwenRepo():BaseRepository(){
    private val PAGE_SIZE=10
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
    suspend fun alreadyBuy(page: String): BaseResponse<AlreadyBuyModel?> = request {
        mService.alreadyBuy(page)
    }

    /***
     * 已购-课程列表
     */
    suspend fun getCourse(id:String): BaseResponse<Course?> = request {
        mService.getCourse(id)
    }

    /***
     * 已购-保存课程播放记录
     */
    suspend fun storageRecord(id:String,courses_id:String,time:String): BaseResponse<String?> = request {
        mService.storageRecord(id,courses_id,time)
    }

    /***
     * 已购-二级页分页
     */
    suspend fun pageYiGou(type:String,page:String): BaseResponse<YiGouPage?> = request {
        mService.pageYiGou(type, page)
    }

    /***
     * 打卡
     */
    suspend fun doPunch(product:String,course:String): BaseResponse<String?> = request {
        mService.doPunch(product, course)
    }

    /***
     * 微信支付
     */
    suspend fun weiChatPay(amount:String,subject:String,order:String): BaseResponse<WeiXinPay?> = request {
        mService.weiChatPay(amount, subject, order)
    }

    /***
     * 微信支付
     */
    suspend fun aliPay(amount:String,subject:String,order:String): BaseResponse<String?> = request {
        mService.aliPay(amount, subject, order)
    }

    /***
     * 注销
     */
    suspend fun logOff(): BaseResponse<String?> = request {
        mService.logOff()
    }

    /***
     * 订单列表
     */
    suspend fun orderList(status:String,page: String): BaseResponse<OrderListModel?> = request {
        mService.orderList(status, page)

    }

    /***
     * 保存配音
     */
    suspend fun storageDub(courseid:String,path:String): BaseResponse<EmptyModel?> = request {
//        val mtoken = RequestBody.create(null, token)
        val file = File(path)
        val requestBody: MultipartBody.Part = RequestFileUtil.uploadFile("url", file)
        val courses_id= courseid.toRequestBody(null)
        mService.storageDub(courses_id,requestBody)
    }


//    fun orderListPage(): Flow<PagingData<OrderListData>> {
//        return Pager(config = PagingConfig(PAGE_SIZE, prefetchDistance = 0), pagingSourceFactory = {
//            OrderListPagingSource(mService)
//        }).flow
//    }

    /***
     * 配音列表
     */
    suspend fun dublist():BaseResponse<List<DubListModel>?> =request{
        mService.getDub()
    }


    /***
     * 分享内容
     */
    suspend fun getShare(id: String):BaseResponse<Share?> =request{
        mService.getShare(id)
    }

    /***
     * 用户反馈
     */
    suspend fun feedback(token: String,content: String):BaseResponse<Any?> =request{
        mService.feedback(token,content)
    }
}
