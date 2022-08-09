package com.example.time_project.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding

import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

import com.example.time_project.R
import com.example.time_project.base.BaseFragment
import com.example.time_project.bean.home.Product

import com.example.time_project.bean.yigou.*
import com.example.time_project.databinding.ProjectFragmentBinding
import com.example.time_project.imp.HoverHeaderModel
import com.example.time_project.start
import com.example.time_project.toast
import com.example.time_project.util.IntentExtra.Companion.code

import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.vm.OwenViewModel

import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV


class ProjectFragment : BaseFragment(R.layout.project_fragment) {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private val viewModel by viewModels<OwenViewModel>()
    private val mBinding by viewBinding(ProjectFragmentBinding::bind)

    private var total=1

    private var showModel=0// 0 展示的是推荐 1 展示的是已购

    private val refreshOb:Observer<String> = Observer {
        getData()
    }

    private lateinit var token: String

    override fun initData()  {

        getData()
        LiveEventBus.get<String>("refresh").observe(this, refreshOb)
    }


    /***
     * case 1 未登录 显示推荐列表
     * case 2 已经登录 但是没有购买任何商品，显示推荐
     * case 3 已经登录，并且有购买商品，显示已购列表
     */

    /***
     * 获取列表数据
     */
    private fun getData(){
        token= MMKV.defaultMMKV().decodeString("token")?:""
        //已购
        mBinding.productList01.linear().setup {
            addType<Product02> { R.layout.item_product2 }
            addType<Recommend> { R.layout.item_product }
            addType<HoverHeaderModel> { R.layout.layout_hover_header }

            onClick(R.id.root_product){
                if (token.isNullOrBlank()){
                    start(requireActivity(),LoginActivity().javaClass,false)
                }else{
                    when(showModel){
                        0->{
                            requireActivity().intent.code=getModel<Recommend>(modelPosition).code
                            start(requireActivity(),ProductDetailActivity().javaClass,requireActivity().intent)
                        }
                        1->{
                            requireActivity().intent.iproductId=getModel<Product02>(modelPosition).id?:-1
                            start(requireActivity(),CourseDetailActivity().javaClass,requireActivity().intent)
                        }
                    }
                }
            }
            onClick(R.id.tv_seemore){
                requireActivity().intent.iproductId=getModel<HoverHeaderModel>(modelPosition).id?:-1
                requireActivity().intent.courseTitle=getModel<HoverHeaderModel>(modelPosition).header
                start(requireActivity(),MoreProjectActivity().javaClass,requireActivity().intent)
            }
        }

        mBinding.statePurchased.onRefresh {
            if (index<=total){
                viewModel.alreadyBuy(index.toString()).observe(this@ProjectFragment, Observer {
                    it?.run {
                        when(code){
                            1000->{
                                data?.run {
                                    val yigou=getYiGouData(purchased)
                                    if (yigou.isNullOrEmpty()){
                                        showModel=0
                                        recommend?.run {
                                            if (data.isNullOrEmpty()){
                                                mBinding.statePurchased.apply {
                                                    emptyLayout=R.layout.empty_order
                                                }.showEmpty()
                                            }else{
                                                val list= mutableListOf<Any>()
                                                list.add(HoverHeaderModel("推荐",0,0))
                                                data.let { it1 -> list.addAll(it1) }
                                                addData(list)
                                                total=pageCount?:1
                                            }
                                        }

                                    }else{
                                        showModel=1
                                        addData(yigou)
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

        }.autoRefresh(1000)

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