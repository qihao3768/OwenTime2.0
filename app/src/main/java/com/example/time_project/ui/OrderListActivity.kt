package com.example.time_project.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.adapter.OrderListAdapter
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.order.OrderListData
import com.example.time_project.bean.order.OrderListModel
import com.example.time_project.bean.order.OrderModel

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

    private var TOTAL_PAGE:Int=0

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
//        mBinding.pageOrder.preloadIndex=0
//        mBinding.pageOrder.index=1
        getData("1","1")
//        //刷新
//        mBinding.pageOrder.onRefresh {
//            postDelayed({
//                getData("1",index.toString())
//            }, 1000)
//        }.autoRefresh(1500)//下拉刷新
////加载更多
//        mBinding.pageOrder.onLoadMore {
//            if (index <= TOTAL_PAGE) {
//                getData("1", index.toString())// 模拟网络请求2秒后成功, 创建假的数据集
//            } else {
//                finishLoadMoreWithNoMoreData()
//            }
//        }

        mBinding.titleOrder.leftView.fastClick {
            finish()
        }
    }

    /***
     * 测试数据
     */
    private fun getData(status:String,page:String){
        val orderListAdapter=OrderListAdapter()
        mBinding.listOrder.adapter=orderListAdapter
        mViewModel.orderList(status, page).observe(this, Observer {

           lifecycleScope.launch {
               orderListAdapter.submitData(it)
           }
       })
    }

}