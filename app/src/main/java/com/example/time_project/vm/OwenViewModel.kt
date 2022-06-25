package com.example.time_project.vm

import androidx.lifecycle.MutableLiveData
import com.example.time_project.base.BaseResponse

import com.example.time_project.base.BaseViewModel
import com.example.time_project.bean.*
import com.example.time_project.resp.OwenRepo

class OwenViewModel() : BaseViewModel() {
    private val owenReps by lazy { OwenRepo() }
    private val bannerData by lazy { MutableLiveData<HomeModel>() }
    fun getBanner():MutableLiveData<HomeModel>{
        launchUI {
            val result = owenReps.banner()
            bannerData.value = result.data
        }
        return bannerData
    }

    //商品详情
    private val detailData by lazy { MutableLiveData<GoodsDetail>() }
    fun getDetail(code:String):MutableLiveData<GoodsDetail>{
        launchUI {
            val result = owenReps.detail(code)
            detailData.value = result.data
        }
        return detailData
    }



    //确认订单
    private val confirmPageData by lazy { MutableLiveData<ConfirmOrderModel?>() }
    fun confirmPage(map:HashMap<String,Any>):MutableLiveData<ConfirmOrderModel?>{
        launchUI {
            val result = owenReps.confirmPage(map)
            confirmPageData.value = result.data
        }
        return confirmPageData
    }

    //添加地址
    private val addressData by lazy { MutableLiveData<BaseResponse<String?>>() }
    fun saveAddress(map:HashMap<String,Any>):MutableLiveData<BaseResponse<String?>>{
        launchUI {
            val result = owenReps.saveAddress(map)
            addressData.value = result
        }
        return addressData
    }

    //修改地址
    fun changeAddress(map:HashMap<String,Any>):MutableLiveData<BaseResponse<String?>>{
        launchUI {
            val result = owenReps.changeAddress(map)
            addressData.value = result
        }
        return addressData
    }

    //下单
    private val upOrderData=MutableLiveData<BaseResponse<OrderSn?>>()
    fun upOrder(body: UpOrderRequestBody):MutableLiveData<BaseResponse<OrderSn?>>{
        launchUI {
            val result = owenReps.upOrder(body)
            upOrderData.value = result
        }
        return upOrderData
    }

    //已购
    private val alreadyBuyData=MutableLiveData<BaseResponse<AlreadyBuyModel?>>()
    fun alreadyBuy():MutableLiveData<BaseResponse<AlreadyBuyModel?>>{
        launchUI {
            val result = owenReps.alreadyBuy()
            alreadyBuyData.value = result
        }
        return alreadyBuyData
    }

    //已购-课程列表
    private val courseData=MutableLiveData<BaseResponse<Course?>>()
    fun getCourse(id:String):MutableLiveData<BaseResponse<Course?>>{
        launchUI {
            val result = owenReps.getCourse(id)
            courseData.value = result
        }
        return courseData
    }

    //已购-保存播放记录 id 商品id，courseId课程id，time 时长
    private val storageRecordData=MutableLiveData<BaseResponse<String?>>()
    fun storageRecord(id:String,courseId:String,time:String):MutableLiveData<BaseResponse<String?>>{
        launchUI {
            val result = owenReps.storageRecord(id,courseId,time)
            storageRecordData.value = result
        }
        return storageRecordData
    }

    //已购-二级页分页
    private val yiGouPageData=MutableLiveData<BaseResponse<YiGouPage?>>()
    fun pageYiGou(type:String,page:String):MutableLiveData<BaseResponse<YiGouPage?>>{
        launchUI {
            val result = owenReps.pageYiGou(type,page)
            yiGouPageData.value = result
        }
        return yiGouPageData
    }

}