package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.ArticleData
import com.example.owentime.bean.Banner
import com.example.owentime.bean.GoodsBean
import com.example.owentime.bean.HomeModel
import com.example.owentime.resp.ArticleRepo
import com.example.owentime.resp.BannerRepo

import kotlinx.coroutines.flow.collectLatest



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

    private val _articleReps by lazy { ArticleRepo() }
    private var _articleData= MutableLiveData<PagingData<GoodsBean>>()

//    val article get() =_articleData

    fun getArticle():MutableLiveData<PagingData<GoodsBean>> {
         launchUI {
             _articleReps.article().cachedIn(viewModelScope).collectLatest{
                 _articleData.value=it
             }
         }
         return _articleData

    }
}