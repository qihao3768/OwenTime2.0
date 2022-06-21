package com.example.time_project.ui

import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.bean.OrderModel
import com.example.time_project.databinding.ActivityOrderListBinding
import com.example.time_project.start
import com.gyf.immersionbar.ktx.immersionBar

class OrderListActivity : BaseActivity(R.layout.activity_order_list) {

    private val mBinding by viewBinding(ActivityOrderListBinding::bind)
    private val image01="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653729726_c61eaad874317a85.jpg"


    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        val list=getData()
        if (list.isNullOrEmpty()){
            mBinding.stateOrder.showEmpty()
        }else{
            mBinding.listOrder.linear().setup {
                addType<OrderModel> { R.layout.item_order }
                onFastClick(R.id.order_root){
//                    toast(getModel<OrderModel>(modelPosition).price)
                    start(this@OrderListActivity,OrderDetailActivity().javaClass,false)
                }
            }.models=getData()
        }

    }

    /***
     * 测试数据
     */
    private fun getData():List<OrderModel>{
        return listOf(
//            OrderModel(image01,"宝贝高碳钢儿童平衡车...","10","已付款"),
//            OrderModel(image01,"宝贝高碳钢儿童平衡车...","10","已付款"),
//            OrderModel(image01,"宝贝高碳钢儿童平衡车...","100","待付款"),
//            OrderModel(image01,"宝贝高碳钢儿童平衡车...","10","待发货"),
//            OrderModel(image01,"某宝知名品牌1234xxx某宝知名品牌1234xxx","10.01","已付款"),
//            OrderModel(image01,"宝贝高碳钢儿童平衡车...","1000","已付款")
        )
    }

    /***
     *
     */
    private fun cancel(){

    }
}