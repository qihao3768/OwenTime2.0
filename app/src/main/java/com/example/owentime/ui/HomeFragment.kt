package com.example.owentime.ui

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.ArticleAdapter
import com.example.owentime.adapter.NoticeAdapter
import com.example.owentime.base.BaseFragment
import com.example.owentime.bean.Banner
import com.example.owentime.bean.NoticeBean
import com.example.owentime.databinding.HomeFragmentBinding
import com.example.owentime.load
import com.example.owentime.start
import com.example.owentime.vm.HomeViewModel
import com.example.owentime.web.WebActivity
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.ZoomOutPageTransformer
import com.youth.banner.util.LogUtils
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(R.layout.home_fragment){

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by viewModels<HomeViewModel>()
    private val mBinding by viewBinding (HomeFragmentBinding::bind)
    private lateinit var mArticleAdapter: ArticleAdapter


    override fun initData() {

        immersionBar {
            statusBarColor(R.color.FE9520)
//            navigationBarColor(R.color.colorPrimary)
        }
        mBinding.titleHome.leftView.visibility=View.GONE
        mBinding.homeBanner.setBannerRound2(11F)
        mBinding.ivHomeHead.setOnClickListener {
            //判断是否已经登录，如果登录，跳转修改用户信息，否则跳转登录
            start(requireActivity(),LoginActivity().javaClass,false)
        }
        initBanner()
        initArticle()
        initNotice()
    }
    private fun initBanner(){

        viewModel.getBanner().observe(this) {
            it?.run {
                val path = mutableListOf<String>()
                it.forEach { banner ->
                    path.add(banner.imagePath)
                }
                mBinding.homeBanner.addBannerLifecycleObserver(this@HomeFragment).setAdapter(
                    object : BannerImageAdapter<String>(path) {
                        override fun onBindView(
                            holder: BannerImageHolder,
                            data: String,
                            position: Int,
                            size: Int
                        ) {
                            holder.imageView.load(data)
                        }

                        override fun onCreateHolder(
                            parent: ViewGroup?,
                            viewType: Int
                        ): BannerImageHolder {
                            return super.onCreateHolder(parent, viewType)
                        }
                    })
            }
        }
    }
    private fun initArticle(){
        mArticleAdapter = ArticleAdapter(requireActivity(),object : ArticleAdapter.ItemClickListener {
            override fun click(url: String) {

                start(requireActivity(),WebActivity().javaClass,"url",url)
            }
        })
        mBinding.homeList.adapter = mArticleAdapter
        mBinding.homeList.layoutManager=GridLayoutManager( requireActivity(),2)
        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    mArticleAdapter.submitData(it)
                }

            })
    }

    private fun initNotice(){
        //实现1号店和淘宝头条类似的效果
        mBinding.noticeBanner.setAdapter(NoticeAdapter(getTest()))
            .setOrientation(com.youth.banner.Banner.VERTICAL)
//            .setPageTransformer(ZoomOutPageTransformer())
            .setOnBannerListener { data: Any, position: Int ->

            }
    }


  fun getTest():MutableList<NoticeBean>{
      val list= mutableListOf<NoticeBean>()
      list.add(NoticeBean("迪丽热巴"))
      list.add(NoticeBean("佟丽娅"))
      list.add(NoticeBean("拼团即将要在5月20日上线啦！~~~ 爆款来袭5折 起，抄底直降无套路！！！"))
      return list
  }

}