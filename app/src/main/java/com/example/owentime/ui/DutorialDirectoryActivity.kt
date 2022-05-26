package com.example.owentime.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.ArticleAdapter
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityDutorialDirectoryBinding
import com.example.owentime.start
import com.example.owentime.vm.TutorialViewModel
import com.example.owentime.web.WebActivity
import kotlinx.coroutines.launch

class DutorialDirectoryActivity : BaseActivity(R.layout.activity_dutorial_directory) {
    private val mViewModel by viewModels<TutorialViewModel>()
    private val mBinding by viewBinding(ActivityDutorialDirectoryBinding::bind)
    private lateinit var mAdapter:ArticleAdapter
    override fun initData() {
        mAdapter = ArticleAdapter(object : ArticleAdapter.ItemClickListener{
            override fun click(url: String) {
                start(this@DutorialDirectoryActivity,WebActivity().javaClass,"url",url)
            }
        })
        mBinding.directoryList.adapter=mAdapter
        val cid=intent.getIntExtra("cid",0)
        mViewModel.getDirectory(cid,1).observe(this, Observer {
        lifecycleScope.launch {
//            mAdapter.submitData(it)
        }
        })
    }
}