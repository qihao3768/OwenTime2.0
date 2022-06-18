package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData

import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.Banner
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

}