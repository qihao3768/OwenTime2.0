package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.WxArticleDetail
import com.example.owentime.bean.WxArticleTree
import com.example.owentime.resp.WxArticleListRepo
import com.example.owentime.resp.WxArticleRepo
import kotlinx.coroutines.flow.collectLatest

class PublicViewModel : BaseViewModel() {
    private val _projectRepo by lazy { WxArticleRepo() }
    private val _projectData by lazy { MutableLiveData<List<WxArticleTree>>() }
    fun getWxArticleTree(): MutableLiveData<List<WxArticleTree>> {
        launchUI {
            val result = _projectRepo.wxarticle()
            _projectData.value = result.data
        }
        return _projectData
    }

    private val _projectDRepo by lazy { WxArticleListRepo() }
    private var _projectDData= MutableLiveData<PagingData<WxArticleDetail>>()

//    val article get() =_articleData

    fun getWxArticleDetail(cid:Int):MutableLiveData<PagingData<WxArticleDetail>> {
        launchUI {
            _projectDRepo.wxArticleList(cid).cachedIn(viewModelScope).collectLatest{
                _projectDData.value=it
            }
        }
        return _projectDData

    }
}