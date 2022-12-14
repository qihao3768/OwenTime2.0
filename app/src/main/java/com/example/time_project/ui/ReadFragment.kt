package com.example.time_project.ui

import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.adapter.BannerImageAdapter
import com.example.time_project.adapter.NoticeAdapter
import com.example.time_project.base.BaseFragment
import com.example.time_project.bean.NoticeBean
import com.example.time_project.bean.home.Banner
import com.example.time_project.bean.home.Product
import com.example.time_project.bean.home.Studying
import com.example.time_project.databinding.FragmentGameBinding
import com.example.time_project.databinding.FragmentReadBinding
import com.example.time_project.start
import com.example.time_project.util.IntentExtra.Companion.code
import com.example.time_project.util.IntentExtra.Companion.courseDub
import com.example.time_project.util.IntentExtra.Companion.courseId
import com.example.time_project.util.IntentExtra.Companion.courseTime
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.icode
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.iurl
import com.example.time_project.util.IntentExtra.Companion.position
import com.example.time_project.util.IntentExtra.Companion.shareImage
import com.example.time_project.vm.OwenViewModel
import com.example.time_project.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener

class ReadFragment : BaseFragment(R.layout.fragment_read) {

    companion object{
        fun newInstance() = ReadFragment()
    }
    private val viewModel by viewModels<OwenViewModel>()
    private val mBinding by viewBinding(FragmentReadBinding::bind)



    private lateinit var mmkv: MMKV



    private val refreshOb: Observer<String> = Observer { string ->
        when (string) {
            "login" -> {
                initBanner()
            }
            "logout" -> {
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
        mBinding.homeBanner.setBannerRound2(22F)
        mBinding.homeBanner.indicator = RectangleIndicator(requireActivity())//?????????

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

    fun transfom(time: Int): String {
        val shi = time / 3600
        val fen = time % 3600 / 60
        val miao = time % 3600 % 60
        //shi< 10 ? ("0" + shi) : shi)??????????????????10??????????????????shi,????????????????????????
        return (if (shi < 10) "0$shi" else shi).toString() + ":" + (if (fen < 10) "0$fen" else fen) + ":" + if (miao < 10) "0$miao" else miao
    }

    override fun onResume() {
        super.onResume()
        val token = mmkv.decodeString("token")
        if (token.isNullOrBlank()) {
            //start(requireActivity(), LoginActivity().javaClass, false)
        } else {
            initBanner()
        }
    }



    /***
     * ?????????banner
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
                mBinding.homeBanner.addBannerLifecycleObserver(this@ReadFragment).setAdapter(
                    object : BannerImageAdapter<String>(path) {
                        override fun onBindView(
                            holder: BannerImageHolder?,
                            data: String?,
                            position: Int,
                            size: Int
                        ) {
                            holder?.imageView?.load(data)
                        }
                    })
                mBinding.homeBanner.setOnBannerListener(OnBannerListener<String> { _, position ->

                    val ban: Banner? = banner?.get(position)
                    ban?.run {
                        when (jumpType) {
                            1 -> {
                                val token = mmkv.decodeString("token")
                                val target = if (token.isNullOrBlank()) {
                                    LoginActivity()
                                } else {
                                    requireActivity().intent.iurl = activityLinks
                                    WebActivity()
                                }
                                start(requireActivity(), target.javaClass, requireActivity().intent)
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
               /* it.studying?.run {
                    mBinding.groupPlaying.visibility = View.VISIBLE
                    initPlaying(this)
                }*/

            }
        }


    }

    /***
     * ????????????
     */
    private fun initArticle(list: List<Product>) {

        mBinding.homeList.linear().setup {
            addType<Product> { R.layout.item_product3 }
            onClick(R.id.root_product) {
                if (mmkv.decodeString("token").isNullOrBlank()) {
                    start(requireActivity(), LoginActivity().javaClass, false)
                } else {
                    //????????????????????????????????????code
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
     * ???????????????
     */
    private fun initNotice() {
        //??????1????????????????????????????????????
        mBinding.noticeBanner.setAdapter(NoticeAdapter(getTest()))
            .setOrientation(com.youth.banner.Banner.VERTICAL)
//            .setPageTransformer(ZoomOutPageTransformer())
            .setOnBannerListener { data: Any, position: Int ->

            }
    }

    /***
     * ????????????????????????????????????
     * ?????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????
     * ????????????????????????????????????????????????
     */
    private fun initPlaying(playing: Studying) {

        playing.run {
            mBinding.ivPlayingCourse.load(image ?: "")
            mBinding.tvPlayingTitleSub.text=name?:""
            mBinding.tvPlayingTitle.text = product_name ?: ""
            mBinding.tvPlayingTime.text = time?.let { transfom(it) }
        }
        mBinding.layoutPlaying.setOnClickListener {

//            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),mBinding.ivPlayingCourse,"palying").toBundle()
            requireActivity().intent.courseUrl = playing.url ?: ""
            requireActivity().intent.courseTime = playing.time ?: 0
            requireActivity().intent.courseId = playing.coursesId.toString()
            requireActivity().intent.courseDub = playing.dubCourse ?: ""
            requireActivity().intent.iproductId = playing.productId ?: 0
            requireActivity().intent.position = -1
            requireActivity().intent.courseTitle = playing.product_name?:""
            requireActivity().intent.shareImage = playing.image?:""
            mmkv.encode("courseid",playing.coursesId.toString())
            start(requireActivity(), ExoplayerActivity().javaClass, requireActivity().intent)
        }

    }

    /***
     * ??????????????????
     */
    private fun getTest(): MutableList<NoticeBean> {
        val list = mutableListOf<NoticeBean>()
        list.add(NoticeBean("????????????"))
        list.add(NoticeBean("?????????"))
        list.add(NoticeBean("??????????????????5???20???????????????~~~ ????????????5??? ????????????????????????????????????"))
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get<String>("refresh").removeObserver(refreshOb)
//        LiveEventBus.get<String>("logout").removeObserver(logoutOb)
    }

}