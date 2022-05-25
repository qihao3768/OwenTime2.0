package com.example.owentime.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.TutorialListAdapter
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityTutorialBinding
import com.example.owentime.start
import com.example.owentime.vm.TutorialViewModel

class TutorialActivity : BaseActivity(R.layout.activity_tutorial) {
    private val viewModel by viewModels<TutorialViewModel>()
    private val mBinding by viewBinding(ActivityTutorialBinding::bind)
    private lateinit var mAdapter:TutorialListAdapter
    override fun initData() {
        viewModel.getTutorial().observe(this, Observer {
            it?.run {
                mAdapter = TutorialListAdapter(object : TutorialListAdapter.GotoDetail{
                    override fun detail(cid: Int) {
                        start(this@TutorialActivity,DutorialDirectoryActivity().javaClass,"cid",cid)
                    }
                },this)
                mBinding.tutorialList.adapter=mAdapter
                mBinding.tutorialList.layoutManager=GridLayoutManager(this@TutorialActivity,2)
            }
        })
    }
}