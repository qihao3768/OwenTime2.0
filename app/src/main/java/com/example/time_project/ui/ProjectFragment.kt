package com.example.time_project.ui

import android.content.Intent
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
import com.example.time_project.bean.yigou.AlreadyBuyModel
import com.example.time_project.bean.yigou.Product02
import com.example.time_project.bean.yigou.Purchased
import com.example.time_project.bean.yigou.Recommend
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

class ProjectFragment : BaseFragment(R.layout.project_fragment) {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private val viewModel by viewModels<OwenViewModel>()
    private val mBinding by viewBinding(ProjectFragmentBinding::bind)
    private val mFragmens= mutableListOf<Fragment>()

    private val mmkv=MMKV.defaultMMKV()

    private val refreshOb:Observer<String> = Observer {
        getData()
    }
    private val logoutOb:Observer<String> = Observer {
        getData()
    }

    override fun initData()  {
        getData()
        LiveEventBus.get<String>("refresh").observe(this, refreshOb)
        LiveEventBus.get<String>("logout").observe(this, logoutOb)
    }


    /***
     * case 1 未登录 显示推荐列表
     * case 2 已经登录 但是没有购买任何商品，显示推荐
     * case 3 已经登录，并且有购买商品，显示已购列表
     */
    private fun getData(){
        val token=mmkv.decodeString("token")
        viewModel.alreadyBuy().observe(this, Observer {
            it?.run {
                when(code){
                    1000->{
                        val body: AlreadyBuyModel?=data
                        body?.run {
                            val data01=getYiGouData(purchased)
                            if (data01.isNullOrEmpty()){
                                //展示推荐列表
                                mBinding.productList02.visibility=View.VISIBLE
                                if (recommend.data.isNullOrEmpty()){
                                    mBinding.stateWorks.apply {
                                        emptyLayout=R.layout.empty_order
                                    }.showEmpty()
                                }else{
                                    mBinding.productList02.linear().setup {
                                        addType<Recommend> { R.layout.item_product }
                                        addType<HoverHeaderModel> { R.layout.layout_hover_header }
                                        val list= mutableListOf<Any>()
                                        list.add(HoverHeaderModel("推荐",0,0))
                                        list.addAll(recommend.data)

                                        models= list
                                        onClick(R.id.root_product){
                                            val target=if (token.isNullOrEmpty()){
                                                LoginActivity()
                                            }else{
                                                requireActivity().intent.code=getModel<Recommend>(modelPosition).code
                                                ProductDetailActivity()
                                            }
                                            start(requireActivity(),target.javaClass,requireActivity().intent)
                                        }


                                    }
                                }

                            }else{
                                //展示已经购买的课程列表
                                mBinding.productList01.visibility=View.VISIBLE
                                mBinding.productList01.linear().setup {
                                    addType<Product02> { R.layout.item_product2 }
                                    addType<HoverHeaderModel> { R.layout.layout_hover_header }
                                    models=data01
                                    onClick(R.id.root_product){
                                        requireActivity().intent.iproductId=getModel<Product02>(modelPosition).id?:-1
                                        start(requireActivity(),CourseDetailActivity().javaClass,requireActivity().intent)
                                    }
                                    onClick(R.id.tv_seemore){
                                        requireActivity().intent.iproductId=getModel<HoverHeaderModel>(modelPosition).id?:-1
                                        requireActivity().intent.courseTitle=getModel<HoverHeaderModel>(modelPosition).header
                                        start(requireActivity(),MoreProjectActivity().javaClass,requireActivity().intent)
                                    }
                                }
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
    }

    /***
     * 如果没有数据，显示推荐
     */
    private fun getYiGouData(mPurchased:List<Purchased>?):List<Any>{
        if (!mPurchased.isNullOrEmpty()){
            val list= mutableListOf<Any>()
            mPurchased.forEach { index ->
                val product=index.product?: emptyList()
                list.add(HoverHeaderModel(index.name?:"",product.size,index.id?:0))
                list.addAll(index.product?: product)
            }
            return list
        }
        return emptyList()
    }

    private fun getRecommond(mPurchased:List<Purchased>?):List<Product02>?{
        if (!mPurchased.isNullOrEmpty()){
            mPurchased.forEach { index ->
                return index.product
            }
        }
        return emptyList()
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get<String>("refresh").removeObserver(refreshOb)
        LiveEventBus.get<String>("logout").removeObserver(logoutOb)
    }
}