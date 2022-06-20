package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData

import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.Banner
import com.example.owentime.bean.ConfirmOrderModel
import com.example.owentime.bean.GoodsDetail
import com.example.owentime.bean.HomeModel
import com.example.owentime.resp.OwenRepo

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

    //添加地址
    private val addressData by lazy { MutableLiveData<String?>() }
    fun saveAddress(map:HashMap<String,Any>):MutableLiveData<String?>{
        launchUI {
            val result = owenReps.saveAddress(map)
            addressData.value = result.data
        }
        return addressData
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

}