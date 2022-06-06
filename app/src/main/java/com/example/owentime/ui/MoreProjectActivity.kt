package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.GoodsModel
import com.example.owentime.databinding.ActivityMoreProjectBinding
import com.example.owentime.databinding.ProjectFragmentBinding
import com.example.owentime.imp.HoverHeaderModel
import com.gyf.immersionbar.ktx.immersionBar

class MoreProjectActivity : BaseActivity(R.layout.activity_more_project) {
    private val mBinding by viewBinding(ActivityMoreProjectBinding::bind)
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.listMore.linear().setup {
            addType<GoodsModel>(R.layout.item_product)

        }.models=getData()
    }
    private fun getData():MutableList<Any>{
        return mutableListOf(
            GoodsModel("宝贝高碳钢儿童平衡车123","一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","1,000","1w人已购买"),
            GoodsModel("宝贝高碳钢儿童平衡车123","一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","1,000","1w人已购买"),
            GoodsModel("宝贝高碳钢儿童平衡车123","一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","1,000","1w人已购买"),
        )
    }
}