package com.example.time_project.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.yigou.Product02

import com.example.time_project.databinding.ActivityMoreProjectBinding
import com.example.time_project.fastClick
import com.example.time_project.start
import com.example.time_project.toast
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.coroutineScope

class MoreProjectActivity : BaseActivity(R.layout.activity_more_project) {
    private val mBinding by viewBinding(ActivityMoreProjectBinding::bind)
    private val viewModel by viewModels<OwenViewModel>()
    private var total=1//总页数

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }

        mBinding.moreTitle.leftView.fastClick {
            finish()
        }
        mBinding.moreTitle.title=intent.courseTitle
        mBinding.listMore.linear().setup {
            addType<Product02>(R.layout.item_product2)
            onClick(R.id.root_product){
                intent.iproductId=getModel<Product02>(modelPosition).id?:-1
                start(this@MoreProjectActivity,CourseDetailActivity().javaClass,intent)
            }

        }

        mBinding.productPage.onRefresh {
            if (index<=total){
                viewModel.pageYiGou(intent.iproductId.toString(),index.toString()).observe(this@MoreProjectActivity, Observer {
                    it?.run {
                        when(code){
                            1000->{
                                data?.run {
                                    if (data==null){
                                        toast(message.toString())
                                    }else{
                                        total=pageCount?:1
                                        addData(data.product)
                                    }
                                }
                            }
                            401->{
                                toast("登录状态失效，请重新登录")
                            }
                            else->{
                                toast(message.toString())
                            }
                        }
                    }
                })
            }else{
                finishLoadMoreWithNoMoreData()
            }
        }.autoRefresh(1500)

    }

}