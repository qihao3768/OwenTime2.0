package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData

import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.Banner
import com.example.owentime.bean.HomeModel
import com.example.owentime.resp.BannerRepo

class HomeViewModel() : BaseViewModel() {
    private val bannerReps by lazy { BannerRepo() }
    private val bannerData by lazy { MutableLiveData<HomeModel>() }


    fun getBanner():MutableLiveData<HomeModel>{
        launchUI {
            val result = bannerReps.banner()
            bannerData.value = result.data
        }
        return bannerData
    }

}