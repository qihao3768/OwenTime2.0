package com.example.time_project.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.statelayout.StateConfig.emptyLayout
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.order.OrderListData


import com.example.time_project.databinding.ActivityOrderListBinding
import com.example.time_project.fastClick
import com.example.time_project.start
import com.example.time_project.toast
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.launch

class OrderListActivity : BaseActivity(R.layout.activity_order_list) {

    private val mBinding by viewBinding(ActivityOrderListBinding::bind)
    private val mViewModel by viewModels<OwenViewModel>()
    private val image01="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653729726_c61eaad874317a85.jpg"

    private var total:Int=1

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }

        mBinding.listOrder.linear().setup {
            addType<OrderListData> { R.layout.item_order }
            onFastClick(R.id.order_root){
//                    toast(getModel<OrderModel>(modelPosition).price)
                start(this@OrderListActivity,OrderDetailActivity().javaClass,false)
            }

        }
        //下拉刷新
        mBinding.pageOrder.onRefresh {
            if (index<=total){
                mViewModel.orderList("",index.toString()).observe(this@OrderListActivity, Observer {
                    it?.run {
                        when(code){
                            1000->{
                                data?.run {
                                    total=pageCount?:1
                                    if (orderlist.isNullOrEmpty()){
                                        mBinding.pageOrder.apply {
                                            emptyLayout=R.layout.empty_order
                                        }.showEmpty()
                                    }else{
                                        addData(orderlist)
                                    }
                                }

                            }
                            401->{
                                toast("登录状态失效，请重新登录")
                            }else->{
                            toast(message.toString())
                        }
                        }
                    }
                })
            }else{
                finishLoadMoreWithNoMoreData()
            }

        }.autoRefresh(1500)


        mBinding.titleOrder.leftView.fastClick {
            finish()
        }
    }

}