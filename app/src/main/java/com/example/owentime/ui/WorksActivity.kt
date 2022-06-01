package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.WorksBean
import com.example.owentime.databinding.ActivityWorksBinding
import com.example.owentime.databinding.MineFragmentBinding
import com.gyf.immersionbar.ktx.immersionBar

class WorksActivity : BaseActivity(R.layout.activity_works) {
    private val mBinding by viewBinding(ActivityWorksBinding::bind)
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.rvWorks.linear().setup {
            addType<WorksBean>(R.layout.layout_works)
        }.models=getData()
    }

    private fun getData():MutableList<Any>{
        return mutableListOf<Any>().apply {
            for (i in 0..9) add(WorksBean("","绘声绘色","2022-01-01 18:00"))
        }
    }
}