package com.example.owentime.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.IntegralListAdapter
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityIntegralListBinding
import com.example.owentime.vm.IntegralViewModel
import kotlinx.coroutines.launch

class IntegralListActivity : BaseActivity(R.layout.activity_integral_list) {
    private val mBinding by viewBinding(ActivityIntegralListBinding::bind)
    private val mViewModel by viewModels<IntegralViewModel>()
    private val mAdapter:IntegralListAdapter = IntegralListAdapter()
    override fun initData() {
        mBinding.integralList.adapter=mAdapter
        mViewModel.integralList().observe(this, Observer {
            lifecycleScope.launch {
                mAdapter.submitData(it)
            }
        })
    }
}