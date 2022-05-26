package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.*
import com.example.owentime.resp.ProjectRepo
import com.example.owentime.resp.SupProjectRepo
import kotlinx.coroutines.flow.collectLatest

class ProjectViewModel : BaseViewModel() {
    private val _projectRepo by lazy { ProjectRepo() }
    private val _projectData by lazy { MutableLiveData<List<ProjectTree>>() }
    fun getProjectTree():MutableLiveData<List<ProjectTree>>{
        launchUI {
            val result = _projectRepo.project()
            _projectData.value = result.data
        }
        return _projectData
    }

    private val _projectDRepo by lazy { SupProjectRepo() }
    private var _projectDData= MutableLiveData<PagingData<GoodsBean>>()

//    val article get() =_articleData

    fun getProjectDetail(cid:Int):MutableLiveData<PagingData<GoodsBean>> {
        launchUI {
            _projectDRepo.subProject(cid).cachedIn(viewModelScope).collectLatest{
                _projectDData.value=it
            }
        }
        return _projectDData

    }
}