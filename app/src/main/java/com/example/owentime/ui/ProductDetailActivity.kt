package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.utils.setup
import com.example.owentime.R
import com.example.owentime.adapter.ImageTitleHolder
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.bean.FlexTagModel
import com.example.owentime.bean.WorksBean
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.databinding.HomeFragmentBinding
import com.example.owentime.databinding.LayoutExitBinding
import com.example.owentime.databinding.LayoutSpecificationsBinding
import com.example.owentime.load
import com.example.owentime.toast
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class ProductDetailActivity : BaseActivity(R.layout.activity_product_detail) {

    private val mBinding by viewBinding (ActivityProductDetailBinding::bind)

    private var buyDialog = BasePopWindow(this)

    private lateinit var spbinding: LayoutSpecificationsBinding

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.transparent)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        val view=layoutInflater.inflate(R.layout.layout_specifications,null)
        spbinding= LayoutSpecificationsBinding.bind(view)
        // TODO: 请求详情页数据,这里是模拟
        val image01="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653729726_c61eaad874317a85.jpg"
        val image02="https://owen-time-test.oss-cn-beijing.aliyuncs.com/banner/1653645442_f0abe23e97e1da8e.jpg"
        val images:MutableList<String> = mutableListOf(image01,image02)
        initTopBanner(images,"title")
        mBinding.layoutKtBuy.setOnClickListener {
            showBuy()
        }
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
        spbinding.tvSub.isEnabled=false
        spbinding.listSpecification.layoutManager=FlexboxLayoutManager(this)
        spbinding.listSpecification.setup {
            addType<FlexTagModel> { R.layout.layout_flex_tag }
            onClick(R.id.tv_specification){
                toast("123")
            }
        }.models=getData()
        spbinding.tvPlus.setOnClickListener {
            val count=spbinding.tvCount.text.toString()
            spbinding.tvCount.text=count.toInt().plus(1).toString()
            spbinding.tvSub.isEnabled=true
        }

        spbinding.tvSub.setOnClickListener {
            val count=spbinding.tvCount.text.toString()
            spbinding.tvCount.text=count.toInt().minus(1).toString()
            spbinding.tvSub.isEnabled = "1" != count
        }

        spbinding.ivClose.setOnClickListener {
            buyDialog.dismiss()
        }

        buyDialog.contentView=spbinding.root
        buyDialog.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.BOTTOM)
            .setShowAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM)
                    .toShow()
            )
            .setDismissAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM)
                    .toDismiss()
            )
            .showPopupWindow()
    }

    /***
     * 商品规格
     */
    private fun getData():MutableList<Any> {
        return mutableListOf<Any>(FlexTagModel("绘声绘色"),FlexTagModel("1234"),FlexTagModel("XXXL"),FlexTagModel("XL"),FlexTagModel("绘声绘色"))
    }
}