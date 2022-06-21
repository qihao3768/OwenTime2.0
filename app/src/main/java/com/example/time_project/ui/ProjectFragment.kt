package com.example.time_project.ui

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseFragment
import com.example.time_project.bean.Product
import com.example.time_project.databinding.ProjectFragmentBinding
import com.example.time_project.imp.HoverHeaderModel
import com.example.time_project.start
import com.example.time_project.vm.ProjectViewModel
import com.tencent.mmkv.MMKV

class ProjectFragment : BaseFragment(R.layout.project_fragment) {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private val viewModel by viewModels<ProjectViewModel>()
    private val mBinding by viewBinding(ProjectFragmentBinding::bind)
    private val mFragmens= mutableListOf<Fragment>()

    private val mmkv=MMKV.defaultMMKV()

    private var viewType:Int=0//0 推荐 1 已购

    private var pic="https://owen-time.oss-cn-beijing.aliyuncs.com/products/pic/1653710525_9858478fbce7e544.png"

    override fun initData()  {
        initArticle()
//        viewModel.getProjectTree().observe(this, Observer {
//            val tabs= arrayListOf<String>()
//            it.run {
//                forEach { _project->
//                    tabs.add(_project.name)
//                    val subFragment = ProjectSubFragment.newInstance(_project.id)
//                    mFragmens.add(subFragment)
//                }
//            }
//            val adapter=ProjectPagerAdapter(mFragmens,childFragmentManager,lifecycle)
//            mBinding.projectViewpager.adapter=adapter
//            mBinding.projectViewpager.offscreenPageLimit=ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
//            mBinding.projectTab.setViewPager2(mBinding.projectViewpager,tabs)
//            mBinding.projectTab.currentTab = 0
//        })
    }

    private fun initArticle(){
        mBinding.videoList.linear().setup {
            addType<Product>(R.layout.item_product)
            addType<HoverHeaderModel>(R.layout.layout_hover_header)
            models=getData()
            onFastClick(R.id.tv_seemore){
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),mBinding.videoList,"project").toBundle()
                val intent =  Intent(requireActivity(), MoreProjectActivity().javaClass)
                startActivity(intent,bundle)
//                start(requireActivity(),MoreProjectActivity().javaClass,false)
            }
            onFastClick(R.id.root_product){
                //如果没有登录，登录，登录之后再点击，进到购买流程
                when(viewType){
                   0->{
                       start(requireActivity(),LoginActivity().javaClass,false)
                   }
                    1->{
                        start(requireActivity(),ProductDetailActivity().javaClass,false)
                    }2->{
                    start(requireActivity(),CourseDetailActivity().javaClass,false)
                    }
                }
            }
            setAnimation(AnimationType.SLIDE_BOTTOM)
        }

        // 可选项, 粘性监听器
//        onHoverAttachListener = object : OnHoverAttachListener {
//            override fun attachHover(v: View) {
//                ViewCompat.setElevation(v, 10F) // 悬停时显示阴影
//            }
//
//            override fun detachHover(v: View) {
//                ViewCompat.setElevation(v, 0F) // 非悬停时隐藏阴影
//            }
//        }

//        mBinding.videoList.adapter = mArticleAdapter
//        mBinding.videoList.layoutManager= GridLayoutManager( requireActivity(),2)
//        viewModel.getProjectDetail(1).observe(viewLifecycleOwner, Observer {
//            lifecycleScope.launch {
//                mArticleAdapter.submitData(it)
//            }
//
//        })
    }

    /***
     * case 1 未登录 显示推荐列表
     * case 2 已经登录 但是没有购买任何商品，显示推荐
     * case 3 已经登录，并且有购买商品，显示已购列表
     */
    private fun getData():MutableList<Any>{
        val login=mmkv.decodeString("token")
        return if (login.isNullOrBlank()){
            viewType=0
            getTuiJian()
        }else{
            return if (getYiGouData().isEmpty()){
                viewType=1
                getTuiJian()
            }else{
                viewType=2
                getYiGouData()
            }

        }
    }
    private fun getTuiJian():MutableList<Any>{
        return  mutableListOf(
            HoverHeaderModel("推荐"),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
        )

    }

    /***
     * 如果没有数据，显示推荐
     */
    private fun getYiGouData():MutableList<Any>{
        return mutableListOf(
            HoverHeaderModel("游戏王国"),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"123456","宝贝高碳钢儿童平衡车123","123","1234",1000),

            HoverHeaderModel("阅读动画"),
            Product("1",1,pic,"一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","宝贝高碳钢儿童平衡车123","123","1234",1000),

            HoverHeaderModel("创想世界"),
            Product("1",1,pic,"一车三用，低","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角一","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角一车","宝贝高碳钢儿童平衡车123","123","1234",1000),
            Product("1",1,pic,"一车三用，低重心黄金三角一车多","宝贝高碳钢儿童平衡车123","123","1234",1000),

        )
//        return mutableListOf<Any>().apply {
//            for (i in 0..9) add(GoodsModel("宝贝高碳钢儿童平衡车123","一车三用，低重心黄金三角一车多用一车三用，低重心黄金三角一","1,000","1w人已购买"))
//        }
    }
}