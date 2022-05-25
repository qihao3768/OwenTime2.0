package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.ArticleData
import com.example.owentime.bean.Tutorial
import com.example.owentime.resp.DirectoryRepo
import com.example.owentime.resp.TutorialRepo
import kotlinx.coroutines.flow.collectLatest

class TutorialViewModel:BaseViewModel() {
    private val tutorialReps by lazy { TutorialRepo() }
    private val tutorialData by lazy { MutableLiveData<List<Tutorial>>() }


    fun getTutorial(): MutableLiveData<List<Tutorial>> {
        launchUI {
            val result = tutorialReps.tutorial()
            tutorialData.value = result.data
        }
        return tutorialData
    }

    private val directoryReps by lazy { DirectoryRepo() }
    private val directoryData =MutableLiveData<PagingData<ArticleData>>()


    fun getDirectory(cid:Int,orderType:Int):MutableLiveData<PagingData<ArticleData>> {
        launchUI {
            directoryReps.directory(cid, orderType).cachedIn(viewModelScope).collectLatest{
                directoryData.value=it
            }
        }
        return directoryData

    }
}