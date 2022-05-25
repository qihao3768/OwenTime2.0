package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.Integral
import com.example.owentime.resp.IntegralResp
import kotlinx.coroutines.flow.collectLatest

class IntegralViewModel():BaseViewModel() {
    private val _integralReps by lazy { IntegralResp() }
    private var _integralData= MutableLiveData<PagingData<Integral>>()

//    val article get() =_articleData

    fun integralList(): MutableLiveData<PagingData<Integral>> {
        launchUI {
            _integralReps.integer().cachedIn(viewModelScope).collectLatest{
                _integralData.value=it
            }
        }
        return _integralData

    }
}