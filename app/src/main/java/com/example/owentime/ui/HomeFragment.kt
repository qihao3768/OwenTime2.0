package com.example.owentime.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
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
import com.example.owentime.toast
import com.example.owentime.vm.HomeViewModel
import com.example.owentime.web.WebActivity
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
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
    private lateinit var mmkv: MMKV


    override fun initData() {
        mmkv = MMKV.defaultMMKV()
//        immersionBar {
//            statusBarColor(R.color.FE9520)
//        }
        mBinding.titleHome.leftView.visibility=View.GONE
        mBinding.homeBanner.setBannerRound2(11F)
        mBinding.ivHomeHead.setOnClickListener {
            //判断是否已经登录，如果登录，跳转修改用户信息，否则跳转登录
            val target=if (mmkv.decodeBool("islogin",false)){
                PerfectActivity()
            }else{
                LoginActivity()
            }
            start(requireActivity(),target.javaClass,false)
        }
        initBanner()
        initArticle()
        initNotice()
        initPlaying()

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onCaptureSharedElementSnapshot(
                sharedElement: View,
                viewToGlobalMatrix: Matrix,
                screenBounds: RectF?
            ): Parcelable {
                sharedElement.alpha=1F
                return super.onCaptureSharedElementSnapshot(
                    sharedElement,
                    viewToGlobalMatrix,
                    screenBounds
                )
            }
        })
    }
    private fun initBanner(){
            viewModel.getBanner().observe(this) { it ->
                it?.run {
                    val path = mutableListOf<String>()
                    it.banner?.run {
                        forEach { mBanner->
                            path.add(mBanner.url?:"")
                        }
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
                    it.product?.run {
                        toast(size.toString())
                    }
                }
            }


    }
    private fun initArticle(){
        mArticleAdapter = ArticleAdapter(requireActivity(),object : ArticleAdapter.ItemClickListener {
            override fun click() {
                start(requireActivity(),ProductDetailActivity().javaClass,false)
            }
        })
        mBinding.homeList.adapter = mArticleAdapter
//        mBinding.homeList.layoutManager=GridLayoutManager( requireActivity(),2)
        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    mArticleAdapter.submitData(it)
                }

            })
    }

    /***
     * 初始化通知
     */
    private fun initNotice(){
        //实现1号店和淘宝头条类似的效果
        mBinding.noticeBanner.setAdapter(NoticeAdapter(getTest()))
            .setOrientation(com.youth.banner.Banner.VERTICAL)
//            .setPageTransformer(ZoomOutPageTransformer())
            .setOnBannerListener { data: Any, position: Int ->

            }
    }

    /***
     * 是否显示正在进行中的视频
     * 如果是游客模式，不显示
     * 如果已经登录，但是正在观看的视频，也不显示
     * 已经登录且有正在观看的视频，显示
     */
    private fun initPlaying(){
    val visibility=if (mmkv.decodeBool("islogin",false)){
        View.VISIBLE
    }else{
        View.GONE
    }
    mBinding.groupPlaying.visibility=visibility
        mBinding.layoutPlaying.setOnClickListener {

            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),mBinding.ivPlayingCourse,"palying").toBundle()
            val intent =  Intent(requireActivity(), ExoplayerActivity().javaClass)
            startActivity(intent,bundle)

//            start(requireActivity(),ExoplayerActivity().javaClass,false)
        }

}

    /***
     * 一些测试数据
     */
  private fun getTest():MutableList<NoticeBean>{
      val list= mutableListOf<NoticeBean>()
      list.add(NoticeBean("迪丽热巴"))
      list.add(NoticeBean("佟丽娅"))
      list.add(NoticeBean("拼团即将要在5月20日上线啦！~~~ 爆款来袭5折 起，抄底直降无套路！！！"))
      return list
  }

}