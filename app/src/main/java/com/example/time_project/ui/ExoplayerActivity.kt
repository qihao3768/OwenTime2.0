package com.example.time_project.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.databinding.ActivityExoplayerBinding
import com.example.time_project.databinding.LayoutCustomExo2Binding
import com.example.time_project.databinding.LayoutShareBinding
import com.example.time_project.util.IntentExtra.Companion.courseId
import com.example.time_project.util.IntentExtra.Companion.courseTime
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.vm.OwenViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ExoPlaybackException.TYPE_SOURCE
import com.google.android.exoplayer2.Player.PositionInfo
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig


/***
 * 视频播放
 */
class ExoplayerActivity : BaseActivity(R.layout.activity_exoplayer) {
    private val mBinding by viewBinding(ActivityExoplayerBinding::bind)
//    private var mUrl="https://owen-time-test.oss-cn-beijing.aliyuncs.com/courses/cou/1643348728_216a94a44ba39a712.mp4"
    private lateinit var exoPlayer:ExoPlayer
    private var isLock = false //是否锁屏

    private lateinit var shareDialog: BasePopWindow//分享面板
    private lateinit var shareBinding: LayoutShareBinding

    private lateinit var mShareAction: ShareAction//分享

    private val viewModel by viewModels<OwenViewModel>()

    private var postion:Long=0L//视频播放进度

     override fun initData() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        immersionBar {
            titleBar(mBinding.exoTitle,false)
            transparentBar()
            hideBar(BarHide.FLAG_HIDE_BAR)
            init()
        }

        //初始化播放器
        val uri = Uri.parse(intent.courseUrl)
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT

        //初始化播放器控件
        val view = layoutInflater.inflate(R.layout.layout_custom_exo2, null, false)

         val binding:LayoutCustomExo2Binding = LayoutCustomExo2Binding.bind(view)
         binding.exoPause.setOnClickListener {
             // TODO: 报错播放记录
             val postion = exoPlayer.contentPosition / 1000
             storageRecord(postion.toString())
         }
        val include=layoutInflater.inflate(R.layout.include_exo3,null,true)
         //分享
         val share=include.findViewById<ImageView>(R.id.video_share)
         share.setOnClickListener {
             share()
         }

         //投屏，暂时不做
//         val forscreen=include.findViewById<TextView>(R.id.tv_forscreen)
//         forscreen.setOnClickListener {
//
//         }
         mBinding.exoPlayer.overlayFrameLayout?.addView(include)
         mBinding.exoPlayer.keepScreenOn=true
         mBinding.exoPlayer.controllerAutoShow=true
         mBinding.exoPlayer.resizeMode=AspectRatioFrameLayout.RESIZE_MODE_FIT
//        mBinding.exoPlayer.setShutterBackgroundColor(R.color.transparent)
         mBinding.exoTitle.title=intent.courseTitle
         val item = MediaItem.fromUri(uri)
//         val item2=MediaItem.fromUri(mUrl)
         exoPlayer.setMediaItem(item)
//         exoPlayer.setMediaItem(item2)
         exoPlayer.seekTo(intent.courseTime.toLong())
         exoPlayer.prepare()
         registerVideoListener(playerListener)

         mBinding.exoPlayer.player=exoPlayer
         mBinding.ivUnlock.setOnClickListener {
             mBinding.ivUnlock.setBackgroundResource(if (isLock) R.drawable.icon_player_unlock else R.drawable.icon_player_lock)
             mBinding.exoPlayer.useController = isLock
             mBinding.exoPlayer.controllerAutoShow = isLock
             if (isLock) {
                 mBinding.exoPlayer.showController()
             } else {
                 mBinding.exoPlayer.hideController()
             }
             isLock = !isLock
         }

         //初始化分享面板
         val panel=layoutInflater.inflate(R.layout.layout_share,null)
         shareBinding = LayoutShareBinding.bind(panel)

         mBinding.exoTitle.leftView.fastClick {

         }
    }


    /***
     * 停止播放
     */
    private fun stop(){
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying){
                exoPlayer.stop()
                exoPlayer.release()
                val postion = exoPlayer.contentPosition / 1000
                storageRecord(postion.toString())
            }
        }
    }
    private fun pause(){
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying){
                exoPlayer.pause()
                val postion = exoPlayer.contentPosition / 1000
                storageRecord(postion.toString())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stop()
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    /***
     * 分享
     */
    private fun share(){
        mShareAction = ShareAction(this)
        shareDialog = BasePopWindow(this)
        shareDialog.contentView=shareBinding.root
        shareBinding.shareClose.setOnClickListener {
            shareDialog.dismiss()
        }
        shareBinding.shareQuan.setOnClickListener {
            shareWX(SHARE_MEDIA.WEIXIN_CIRCLE)

        }
        shareBinding.shareWechat.setOnClickListener {
            shareWX(SHARE_MEDIA.WEIXIN)
        }
        shareDialog.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.BOTTOM)
            .setShowAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM)
                    .toShow()
            )
            .setDismissAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM)
                    .toDismiss()
            )
            .setFitSize(true)
            .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
            .showPopupWindow()

    }

    /***
     * 分享到微信
     * platform 平台 微信、朋友圈
     */
    private fun shareWX(platform:SHARE_MEDIA){
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

    /****
     * 注册播放器监听事件
     */
    private fun registerVideoListener(listener: Player.Listener) {
        exoPlayer.addListener(listener)
    }
    //播放器监听器
    private val playerListener: Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_IDLE -> {}
                Player.STATE_BUFFERING -> {}
                Player.STATE_READY -> {}
                Player.STATE_ENDED -> {
//                    share()
                    doPunch(intent.iproductId.toString(),intent.courseId.toString())
                }
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            if (playWhenReady){
                //进入到播放页面的时候先保存一次播放记录，目的是防止应用大退时无法保存
                storageRecord("0")
            }
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {

        }

        override fun onPlayerError(error: PlaybackException) {
            when(error.errorCode){
                    PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS->{toast("播放失败")}
                PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND->{
                    toast("找不到视频文件")}
                PlaybackException.ERROR_CODE_IO_NO_PERMISSION->{
                    toast("缺少权限")}
                else->{
                    toast("播放失败")
                }

            }
            super.onPlayerError(error)
        }
        override fun onPositionDiscontinuity(
            oldPosition: PositionInfo,
            newPosition: PositionInfo,
            reason: Int
        ) {
            super.onPositionDiscontinuity(oldPosition, newPosition, reason)
        }
    }

    /***
     * 保存播放记录
     * iproductId 商品id
     * courseId 课程id
     * time 时长
     */
    private fun storageRecord(time:String){
        viewModel.storageRecord(intent.iproductId.toString(),intent.courseId.toString(),time).observe(this@ExoplayerActivity,
            Observer {
                it?.run {
                    when(code){
                        1000->{

                        }
                        401->{
                            toast("登录状态已失效，请重新登录")
                            start(this@ExoplayerActivity,LoginActivity().javaClass,true)
                        }else->{
                        toast(message.toString())
                    }
                    }
                }
            })
    }

    private var exitTime=0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-exitTime>2000){
                toast("再按一次退出播放")
                exitTime = System.currentTimeMillis()
            }else{
                stop()
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.removeListener(playerListener)
        exoPlayer.release()
    }

    /***
     * 打卡
     */
    private fun doPunch(product:String,course:String){
        viewModel.doPunch(product, course).observe(this, Observer {
            it?.run {
                when(code){
                    1000->{
                        toast("解锁成功")
                        share()
                    }
                    401->{

                    }
                    else->{
                        toast(message.toString())
                    }
                }
            }
        })
    }

}