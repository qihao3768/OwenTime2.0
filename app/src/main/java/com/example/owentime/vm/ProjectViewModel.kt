package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.*
import com.example.owentime.resp.ProjectRepo
import kotlinx.coroutines.flow.collectLatest

class ProjectViewModel : BaseViewModel() {
//    private val _projectRepo by lazy { ProjectRepo() }
//    private val _projectData by lazy { MutableLiveData<List<ProjectTree>>() }
//    fun getProjectTree():MutableLiveData<List<ProjectTree>>{
//        launchUI {
//            val result = _projectRepo.project()
//            _projectData.value = result.data
//        }
//        return _projectData
//    }

}