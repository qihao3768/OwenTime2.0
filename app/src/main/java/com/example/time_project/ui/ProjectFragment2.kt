package com.example.time_project.ui

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseFragment
import com.example.time_project.bean.*
import com.example.time_project.bean.yigou.*
import com.example.time_project.databinding.ProjectFragmentBinding
import com.example.time_project.imp.HoverHeaderModel
import com.example.time_project.start
import com.example.time_project.toast
import com.example.time_project.ui.UpOrderActivity.IntentOptions.iid
import com.example.time_project.util.IntentExtra.Companion.code
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.vm.OwenViewModel
import com.example.time_project.vm.ProjectViewModel
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class ProjectFragment2 : BaseFragment(R.layout.project_fragment2) {

    companion object {
        fun newInstance() = ProjectFragment2()
    }

    private val viewModel by viewModels<ProjectViewModel>()
    private val mBinding by viewBinding(ProjectFragmentBinding::bind)
    private val mFragmens= mutableListOf<Fragment>()

    private val mmkv=MMKV.defaultMMKV()

    private val refreshOb:Observer<String> = Observer {
        when(it){
            "login"->{
                getData()
            }
            "logout"->{
                getData()
            }
        }

    }

    override fun initData()  {
        getData()
        LiveEventBus.get<String>("refresh").observe(this, refreshOb)
    }



    /***
     * 获取列表数据
     */
    private fun getData(){
        viewModel.alreadyBuy().observe(this, Observer {
            it?.run {
                when(code){
                    1000->{
                        data?.run {

                        }
                    }
                    401->{

                    }
                    else->{
                        toast(message.toString())
                    }

                }
            }
        })
    }

    /***
     * 如果没有数据，显示推荐
     */
    private fun getYiGouData(mPurchased:List<Purchased>?):List<Any>{
        val list= mutableListOf<Any>()
        if (!mPurchased.isNullOrEmpty()){
//            val list= mutableListOf<Any>()
            mPurchased.forEach { index ->
                val product=index.product?: emptyList()
                list.add(HoverHeaderModel(index.name?:"",product.size,index.id?:0))
                list.addAll(index.product?: product)
            }
            return list
        }
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get<String>("refresh").removeObserver(refreshOb)
//        LiveEventBus.get<String>("logout").removeObserver(logoutOb)
    }



}