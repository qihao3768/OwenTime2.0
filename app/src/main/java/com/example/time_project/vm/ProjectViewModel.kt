package com.example.time_project.vm

import androidx.lifecycle.MutableLiveData
import com.example.time_project.base.BaseResponse
import com.example.time_project.base.BaseViewModel
import com.example.time_project.bean.yigou.AlreadyBuyModel
import com.example.time_project.resp.OwenRepo

class ProjectViewModel : BaseViewModel() {
    private val owenReps by lazy { OwenRepo() }

//已购
private val alreadyBuyData= MutableLiveData<BaseResponse<AlreadyBuyModel?>>()
    fun alreadyBuy(page:String): MutableLiveData<BaseResponse<AlreadyBuyModel?>> {
        launchUI {
            val result = owenReps.alreadyBuy(page)
            alreadyBuyData.value = result
        }
        return alreadyBuyData
    }
}