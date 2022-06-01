package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.ImageTitleHolder
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.databinding.HomeFragmentBinding
import com.example.owentime.load
import com.gyf.immersionbar.ktx.immersionBar
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

class ProductDetailActivity : BaseActivity(R.layout.activity_product_detail) {

    private val mBinding by viewBinding (ActivityProductDetailBinding::bind)

    private var buyDialog = BasePopWindow(this)

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.transparent)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        // TODO: 请求详情页数据
        val image01="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653729726_c61eaad874317a85.jpg"
        val image02="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653645442_f0abe23e97e1da8e.jpg"
        val images:MutableList<String> = mutableListOf(image01,image02)
        initTopBanner(images,"title")
    }

    //初始化顶部banner
    private fun initTopBanner(headImage: List<String>, title: String) {
        mBinding.groupBanner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(object : BannerAdapter<String, ImageTitleHolder>(headImage) {
                override fun onCreateHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ImageTitleHolder {
                    return ImageTitleHolder(
                        layoutInflater.inflate(
                            R.layout.banner_image_title,
                            parent,
                            false
                        )
                    )
                }

                override fun onBindView(
                    holder: ImageTitleHolder,
                    data: String,
                    position: Int,
                    size: Int
                ) {
//                    val options = RequestOptions().centerCrop().error(R.drawable.aa)
//                    Glide.with(this).setDefaultRequestOptions(options)
//                        .load(data).into(holder.imageView)
                    holder.imageView.load(data)
                    holder.title.text = title
                }

            }).start().indicator = CircleIndicator(this)

        mBinding.groupBanner.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                mBinding.groupPicCount.text = position.plus(1).toString().plus("/").plus(headImage.size)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /***
     * 显示商品规格
     */
    private fun showBuy(){

    }
}