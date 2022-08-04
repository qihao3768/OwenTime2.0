package com.example.time_project.ui

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
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load

import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.*
import com.example.time_project.adapter.NoticeAdapter
import com.example.time_project.base.BaseFragment
import com.example.time_project.bean.NoticeBean
import com.example.time_project.bean.home.Banner
import com.example.time_project.bean.home.Product
import com.example.time_project.bean.home.Studying
import com.example.time_project.databinding.HomeFragmentBinding
import com.example.time_project.ui.UpOrderActivity.IntentOptions.iname
import com.example.time_project.util.DateUtil
import com.example.time_project.util.IntentExtra.Companion.code
import com.example.time_project.util.IntentExtra.Companion.courseTime
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.iBirthday
import com.example.time_project.util.IntentExtra.Companion.iHead
import com.example.time_project.util.IntentExtra.Companion.iSex
import com.example.time_project.util.IntentExtra.Companion.iSkip
import com.example.time_project.util.IntentExtra.Companion.iUserName
import com.example.time_project.util.IntentExtra.Companion.icode
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.iurl
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.MineViewModel
import com.example.time_project.vm.OwenViewModel
import com.example.time_project.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.BaseIndicator
import com.youth.banner.indicator.Indicator
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.indicator.RoundLinesIndicator
import com.youth.banner.listener.OnBannerListener
import kotlin.random.Random

class HomeFragment : BaseFragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()

//        var Intent.code by IntentExtraString("code")
//
//        var Intent.iurl by IntentExtraString("url")
    }

    private val viewModel by viewModels<OwenViewModel>()
    private val mBinding by viewBinding(HomeFragmentBinding::bind)
    private val mViewModel by viewModels<MineViewModel>()


    private lateinit var mmkv: MMKV

    private var studying: Studying? = null//正在观看的视频
    private var mUserName: String? = ""//用户名
    private var mSex: Int = 0//性别
    private var mBirth: String? = ""//生日
    private var mHead: String? = ""//头像

    private val refreshOb: Observer<String> = Observer { string ->
        when (string) {
            "login" -> {
                initBanner()
            }
            "logout" -> {
                mBinding.ivHomeHead.load(R.drawable.logo)
                mBinding.groupPlaying.visibility = View.GONE
            }
        }

    }
//    private val logoutOb:Observer<String> = Observer {
//        it?.run {
//
//        }
//    }


    override fun initData() {
        mmkv = MMKV.defaultMMKV()
        immersionBar {
            statusBarColor(R.color.FE9520)
            keyboardEnable(false)
            statusBarDarkFont(false)
            fitsSystemWindows(true)
        }
        mBinding.titleHome.leftView.visibility = View.GONE
        mBinding.homeBanner.setBannerRound2(22F)
        mBinding.homeBanner.indicator = RectangleIndicator(requireActivity())//指示器
        mBinding.ivHomeHead.setOnClickListener {
            //判断是否已经登录，如果登录，跳转修改用户信息，否则跳转登录
            val target = if (mmkv.decodeString("token").isNullOrBlank()) {
                LoginActivity()

            } else {
                requireActivity().intent.iSkip = true
                requireActivity().intent.iUserName = mUserName
                requireActivity().intent.iSex = mSex
                requireActivity().intent.iBirthday = mBirth
                requireActivity().intent.iHead = mHead
                PerfectActivity()
            }
            start(requireActivity(), target.javaClass, requireActivity().intent)
        }
        initBanner()
        LiveEventBus.get<String>("refresh").observe(this, refreshOb)
//        LiveEventBus.get<String>("logout").observe(this, logoutOb)
        initNotice()

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onCaptureSharedElementSnapshot(
                sharedElement: View,
                viewToGlobalMatrix: Matrix,
                screenBounds: RectF?
            ): Parcelable {
                sharedElement.alpha = 1F
                return super.onCaptureSharedElementSnapshot(
                    sharedElement,
                    viewToGlobalMatrix,
                    screenBounds
                )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val token = mmkv.decodeString("token")
        if (token.isNullOrBlank()) {
            start(requireActivity(), LoginActivity().javaClass, false)
        } else {
            getUser(token)
        }
    }

    private fun getUser(mToken: String) {
        mViewModel.getUser(mToken).observe(requireActivity(), Observer {
            it?.run {
                mBinding.ivHomeHead.load(photo ?: "") {
                    placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                }
                mUserName = name ?: ""
                mSex = sex ?: 0
                mBirth = birthday ?: ""
                mHead = photo ?: ""
            }
        })


    }

    /***
     * 初始化banner
     */
    private fun initBanner() {
        viewModel.getBanner().observe(this) { it ->
            it?.run {
                val path = mutableListOf<String>()
                it.banner?.run {
                    forEach { mBanner ->
                        path.add(mBanner.url ?: "")
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
                mBinding.homeBanner.setOnBannerListener(OnBannerListener<String> { _, position ->

                    val ban: Banner? = banner?.get(position)
                    ban?.run {
                        when (jumpType) {
                            1 -> {
                                requireActivity().intent.iurl = activityLinks
                                start(
                                    requireActivity(),
                                    WebActivity().javaClass,
                                    requireActivity().intent
                                )
                            }
                            4 -> {
                                val token = mmkv.decodeString("token")
                                val target = if (token.isNullOrBlank()) {
                                    LoginActivity()
                                } else {
                                    requireActivity().intent.icode = productCode
                                    ProductDetailActivity()
                                }
                                start(requireActivity(), target.javaClass, requireActivity().intent)
                            }
                        }
                    }
                })
                it.product?.run {
                    initArticle(this)
                }
                it.studying?.run {
                    mBinding.groupPlaying.visibility = View.VISIBLE
                    initPlaying(this)
                }
                it.user?.run {
                    mBinding.ivHomeHead.load(photo ?: "")
                    mUserName = name ?: ""
                    mSex = sex ?: 0
                    mBirth = birthday ?: ""
                    mHead = photo ?: ""
                }
            }
        }


    }

    /***
     * 首页推荐
     */
    private fun initArticle(list: List<Product>) {

        mBinding.homeList.linear().setup {
            addType<Product> { R.layout.item_product }
            onClick(R.id.root_product) {
                if (mmkv.decodeString("token").isNullOrBlank()) {
                    start(requireActivity(), LoginActivity().javaClass, false)
                } else {
                    //跳到详情页，携带一个商品code
                    requireActivity().intent.code = getModel<Product>(modelPosition).code
                    start(
                        requireActivity(),
                        ProductDetailActivity().javaClass,
                        requireActivity().intent
                    )
                }
            }
        }.models = list
    }

    /***
     * 初始化通知
     */
    private fun initNotice() {
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
     * 如果已经登录，但是没有正在观看的视频，也不显示
     * 已经登录且有正在观看的视频，显示
     */
    private fun initPlaying(playing: Studying) {

        playing.run {
            mBinding.ivPlayingCourse.load(image ?: "")
            mBinding.tvPlayingTitle.text = name ?: ""
            mBinding.tvPlayingTime.text = time.toString()
        }
        mBinding.layoutPlaying.setOnClickListener {

//            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),mBinding.ivPlayingCourse,"palying").toBundle()
            requireActivity().intent.courseUrl = playing.url ?: ""
            requireActivity().intent.courseTime = playing.time ?: 0
            start(requireActivity(), ExoplayerActivity().javaClass, requireActivity().intent)
        }

    }

    /***
     * 一些测试数据
     */
    private fun getTest(): MutableList<NoticeBean> {
        val list = mutableListOf<NoticeBean>()
        list.add(NoticeBean("迪丽热巴"))
        list.add(NoticeBean("佟丽娅"))
        list.add(NoticeBean("拼团即将要在5月20日上线啦！~~~ 爆款来袭5折 起，抄底直降无套路！！！"))
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get<String>("refresh").removeObserver(refreshOb)
//        LiveEventBus.get<String>("logout").removeObserver(logoutOb)
    }

}