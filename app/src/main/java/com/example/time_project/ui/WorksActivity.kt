package com.example.time_project.ui

import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drake.brv.item.ItemExpand
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.bean.mine.Dub
import com.example.time_project.bean.mine.DubGroupModel
import com.example.time_project.bean.mine.DubListModel
import com.example.time_project.bean.other.WorksBean
import com.example.time_project.databinding.ActivityWorksBinding
import com.example.time_project.databinding.LayoutShareBinding
import com.example.time_project.fastClick
import com.example.time_project.start
import com.example.time_project.toast
import com.example.time_project.util.IntentExtra.Companion.courseId
import com.example.time_project.util.IntentExtra.Companion.courseTime
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.position
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

/***
 * 我的作品
 */
class WorksActivity : BaseActivity(R.layout.activity_works) {
    private val mBinding by viewBinding(ActivityWorksBinding::bind)
    private val viewModel by viewModels<OwenViewModel>()
    companion object IntentOptions{
        var Intent.url by IntentExtraString("url")
    }
    var url_list = arrayListOf<String>()
    private lateinit var shareDialog: BasePopWindow//分享面板
    private lateinit var shareBinding: LayoutShareBinding

    private lateinit var mShareAction: ShareAction//分享
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        getData()

        mBinding.titleWorks.leftView.fastClick {
            finish()
        }
    }

    private fun getData(){
        mBinding.rvWorks.linear().setup {
            addType<DubGroupModel>(R.layout.item_dub_first)
            addType<Dub>(R.layout.layout_works)
            R.id.item.onFastClick {
                if (getModel<ItemExpand>().itemExpand) {expandOrCollapse()
                } else {expandOrCollapse()}
            }
            onFastClick(R.id.btn_play){
                intent.courseUrl = getModel<Dub>().url?: ""
                intent.courseTime =0
                intent.courseId = getModel<Dub>().courseId.toString()
                intent.position=-1
               // intent.putStringArrayListExtra("url_list",url_list)
                start(this@WorksActivity, ExoplayerActivity().javaClass, intent)
            }
            onFastClick(R.id.layout_sharewx){

                shareWX(SHARE_MEDIA.WEIXIN)
            }

            onFastClick(R.id.layout_sharequan){

                shareWX(SHARE_MEDIA.WEIXIN_CIRCLE)
            }
        }
        mBinding.worksRefresh.onRefresh {

            viewModel.dublist().observe(this@WorksActivity, Observer {
                it?.run {
                    when(code){
                        1000->{
                            val list= mutableListOf<DubGroupModel>()
                            data?.forEach { _data->
                                val model=DubGroupModel().apply {
                                    name=_data.name
                                    image=_data.image
                                    time= _data.dub?.get(0)?.createdAt
                                    _data.dub?.forEach { _dub->
                                        _dub?.name=_data.name
                                        _dub?.image=_data.image
                                        url_list.add(_dub?.url.toString())
                                    }
                                    itemSublist=_data.dub
                                }
                                list.add(model)

                            }
                           addData(list)
                        }
                        401->{
                            toast("登录状态已失效,请重新登录")
                        }
                        1100->{
                            mBinding.worksRefresh.apply {
                                emptyLayout=R.layout.empty_works
                            }.showEmpty()
                        }
                        else->{
                            toast(message.toString())
                        }

                    }
                }
            })
        }.autoRefresh(1000)

    }

    /***
     * 分享到微信
     * platform 平台 微信、朋友圈
     */
    private fun shareWX(platform: SHARE_MEDIA){
        mShareAction = ShareAction(this)
        val umImage = UMImage(this, R.drawable.share_tiyan)
        mShareAction.setPlatform(platform)
            .withMedia(umImage)
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