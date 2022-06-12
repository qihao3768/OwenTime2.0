package com.example.owentime.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.databinding.ActivityExoplayerBinding
import com.example.owentime.databinding.LayoutExitBinding
import com.example.owentime.databinding.LayoutShareBinding
import com.example.owentime.toast
import com.example.owentime.ui.WorksActivity.IntentOptions.url
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class ExoplayerActivity : BaseActivity(R.layout.activity_exoplayer) {
    private val mBinding by viewBinding(ActivityExoplayerBinding::bind)
    private var mUrl="https://owen-time-test.oss-cn-beijing.aliyuncs.com/courses/cou/1643348728_216a94a44ba39a712.mp4"
    private lateinit var exoPlayer:ExoPlayer
    private var isLock = false //是否锁屏

    private lateinit var shareDialog: BasePopWindow//分享面板
    private lateinit var shareBinding: LayoutShareBinding


     override fun initData() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        immersionBar {
            titleBar(mBinding.exoTitle,false)
            transparentBar()
            hideBar(BarHide.FLAG_HIDE_BAR)
            init()
        }
         with(WorksActivity.IntentOptions){
             mUrl=intent.url.toString()
         }
        //初始化播放器
        val uri = Uri.parse(mUrl)
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT

        //初始化播放器控件
        val view = layoutInflater.inflate(R.layout.layout_custom_exo2, null, false)
        val include=layoutInflater.inflate(R.layout.include_exo3,null,true)
         //分享
         val share=include.findViewById<ImageView>(R.id.video_share)
         share.setOnClickListener {
             share()
         }

         //投屏
         val forscreen=include.findViewById<TextView>(R.id.tv_forscreen)
         forscreen.setOnClickListener {

         }


        mBinding.exoPlayer.overlayFrameLayout?.addView(include)
        mBinding.exoPlayer.keepScreenOn=true
        mBinding.exoPlayer.controllerAutoShow=true
        mBinding.exoPlayer.resizeMode=AspectRatioFrameLayout.RESIZE_MODE_FIT
//        mBinding.exoPlayer.setShutterBackgroundColor(R.color.transparent)
        mBinding.exoTitle.title=intent.getStringExtra("title")
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
         //播放
        play(uri)

         //初始化分享面板
         val panel=layoutInflater.inflate(R.layout.layout_share,null)
         shareBinding = LayoutShareBinding.bind(panel)
    }

    /***
     * 开始播放
     */
    private fun play(uri:Uri){
        val item = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(item)
        exoPlayer.prepare()
//        exoPlayer.play()
    }

    /***
     * 停止播放
     */
    private fun stop(){
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying){
                exoPlayer.stop()
                exoPlayer.release()
            }
        }
    }
    private fun pause(){
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying){
                exoPlayer.pause()
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
        shareDialog = BasePopWindow(this)
        shareDialog.contentView=shareBinding.root
        shareBinding.shareClose.setOnClickListener {
            toast("1243")
        }
        shareBinding.shareQuan.setOnClickListener {
            toast("朋友圈")
        }
        shareBinding.shareWechat.setOnClickListener {
            toast("朋友圈")
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
}