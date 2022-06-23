package com.example.time_project.ui

import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.drake.brv.utils.setup
import com.example.time_project.*
import com.example.time_project.adapter.ImageTitleHolder
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.bean.FlexTagModel
import com.example.time_project.bean.Sku
import com.example.time_project.databinding.ActivityProductDetailBinding
import com.example.time_project.databinding.LayoutSpecificationsBinding
import com.example.time_project.load
import com.example.time_project.util.IntentExtra.Companion.code
import com.example.time_project.util.IntentExtra.Companion.icode
import com.example.time_project.util.IntentExtra.Companion.icoupon
import com.example.time_project.util.IntentExtra.Companion.inum
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.isku
import com.example.time_project.util.IntentExtraInt
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.OwenViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.umeng.socialize.ShareAction
import com.umeng.socialize.ShareContent
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class ProductDetailActivity : BaseActivity(R.layout.activity_product_detail) {

    private val mBinding by viewBinding (ActivityProductDetailBinding::bind)

    private var buyDialog = BasePopWindow(this)

    private lateinit var spbinding: LayoutSpecificationsBinding

    private val viewModel by viewModels<OwenViewModel>()

    private var mSku :List<Sku>?= listOf<Sku>()//规格列表

    private var selectSku:Sku?=null

    private var mStock:Int=0//库存

    private lateinit var mShareAction:ShareAction//分享


//    companion object IntentOptions{
//        var Intent.icode by IntentExtraString("code")//商品代码
//        var Intent.isku by IntentExtraString("sku")//skuid
//        var Intent.inum by IntentExtraString("num")//购买数量
//        var Intent.icoupon by IntentExtraString("coupon")//优惠券id
//        var Intent.iproductId by IntentExtraInt("productId")//商品ID
//    }
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

        mBinding.layoutKtBuy.fastClick {
            showBuy()
        }

        mBinding.productTitle.rightView.fastClick {
            mShareAction.open()
        }

    mBinding.productTitle.leftView.fastClick {
        finish()
    }
        //详情
        getDetail(intent.code.toString())
        //分享
        share()
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
//                    holder.title.text = title
                }

            }).start().indicator = CircleIndicator(this)
    }

    /***
     * 显示商品规格
     */
    private fun showBuy(){
        spbinding.tvSub.isEnabled=false
        spbinding.listSpecification.layoutManager=FlexboxLayoutManager(this)
        spbinding.listSpecification.setup {
            addType<Sku> { R.layout.layout_flex_tag }
            onClick(R.id.tv_specification){
                val model=getModel<Sku>(modelPosition)
                model.run {
                    spbinding.tvSkuPrice.text=model.priceActual?:""
                    model.selected=model.selected?.not()
                    notifyDataSetChanged()
                    spbinding.ivSpecification.load(imgShow)
                }
                selectSku=model

            }
            models=mSku
        }
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
        spbinding.btnBuy.setOnClickListener {
            val count=spbinding.tvCount.text.toString().toInt()
            val mStock:Int=selectSku?.stock?:0
            if (selectSku==null){
                toast("请选择规格")
                return@setOnClickListener
            }
            when(count){
                0->{
                    toast("没有库存")
                }
                in 1..mStock ->{
                    buyDialog.dismiss()
                    intent.icode=intent.code
                    intent.isku=selectSku?.id.toString()
                    intent.inum=count.toString()
                    intent.icoupon=""//优惠券暂时没有
                    intent.iproductId=selectSku?.productId?:0
                    start(this@ProductDetailActivity,UpOrderActivity().javaClass,intent)
                }
                else->{
                    toast("请选择购买数量")
                }

            }

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
    private fun getSpecification():MutableList<Any> {
        return mutableListOf<Any>(FlexTagModel("绘声绘色"),FlexTagModel("1234"),FlexTagModel("XXXL"),FlexTagModel("XL"),FlexTagModel("绘声绘色"))
    }

    /***
     * 获取详情数据
     */
    private fun getDetail(code:String){
        viewModel.getDetail(code).observe(this, Observer {
            it?.run {
                val images:MutableList<String> = mutableListOf(imgHead?:"")
                initTopBanner(images,name?:"")
                mBinding.groupDetailPic.loadUrl(detail?:"")
                //价格默认取规格列表的第一条
                mBinding.tvGoodsPrice.text=sku?.get(0)?.priceActual ?: ""
                mBinding.tvGoodsTitle.text=name?:""
                mBinding.tvGoodsDesc.text=introduction?:""
                mSku= sku
            }
        })
    }

    /***
     * 分享
     */
    private fun share() {
        mShareAction = ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).addButton(
            "复制链接","复制链接","umeng_socialize_copyurl","umeng_socialize_copyurl"
        ).setShareboardclickCallback { p0, p1 ->
            when (p0.mShowWord) {
                "复制文本" -> {
                    toast("复制文本")
                }
                "复制链接" -> {
//                    copyLink("https://www.owentime.cn/api/exp/expHtml")
                }
                else -> {
                    val umImage = UMImage(this, R.drawable.share_tiyan)
                    val shareContent = ShareContent()
                    shareContent.mText = "我正在给宝宝报名在家早教训练营，快来帮我点一下！"
                    ShareAction(this).withMedia(umImage)
                        .setPlatform(p1)
                        .setCallback(object : UMShareListener {
                            override fun onStart(p0: SHARE_MEDIA) {

                            }

                            override fun onResult(p0: SHARE_MEDIA) {
                                if (p0.name == "WEIXIN_FAVORITE") {
                                    toast("收藏成功")

                                } else {
                                    if (p0 != SHARE_MEDIA.MORE && p0 != SHARE_MEDIA.SMS
                                        && p0 != SHARE_MEDIA.EMAIL
                                        && p0 != SHARE_MEDIA.FLICKR
                                        && p0 != SHARE_MEDIA.FOURSQUARE
                                        && p0 != SHARE_MEDIA.TUMBLR
                                        && p0 != SHARE_MEDIA.POCKET
                                        && p0 != SHARE_MEDIA.PINTEREST
                                        && p0 != SHARE_MEDIA.INSTAGRAM
                                        && p0 != SHARE_MEDIA.GOOGLEPLUS
                                        && p0 != SHARE_MEDIA.YNOTE &&
                                        p0 != SHARE_MEDIA.EVERNOTE
                                    ) {
                                        toast("分享成功")
                                    }
                                }
                            }

                            override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                                if (p0 != SHARE_MEDIA.MORE && p0 != SHARE_MEDIA.SMS && p0 != SHARE_MEDIA.EMAIL && p0 != SHARE_MEDIA.FLICKR && p0 != SHARE_MEDIA.FOURSQUARE && p0 != SHARE_MEDIA.TUMBLR && p0 != SHARE_MEDIA.POCKET && p0 != SHARE_MEDIA.PINTEREST && p0 != SHARE_MEDIA.INSTAGRAM && p0 != SHARE_MEDIA.GOOGLEPLUS && p0 != SHARE_MEDIA.YNOTE && p0 != SHARE_MEDIA.EVERNOTE) {
                                    toast("分享失败")
                                }

                            }

                            override fun onCancel(p0: SHARE_MEDIA?) {
                                toast("取消分享")
                            }
                        }).share()
                }
            }
        }
    }
}