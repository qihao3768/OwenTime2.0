package com.example.owentime.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityExoplayerBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar

class ExoplayerActivity : BaseActivity(R.layout.activity_exoplayer) {
    private val mBinding by viewBinding(ActivityExoplayerBinding::bind)
    override fun initData() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        immersionBar {
            titleBar(mBinding.exoTitle,false)
            transparentBar()
            hideBar(BarHide.FLAG_HIDE_BAR)
            init()
        }
        val view=layoutInflater.inflate(R.layout.include_exo3,null,true)
        mBinding.exoPlayer.overlayFrameLayout?.addView(view)
        mBinding.exoPlayer.keepScreenOn=true
        mBinding.exoPlayer.controllerAutoShow=true
        mBinding.exoPlayer.resizeMode=AspectRatioFrameLayout.RESIZE_MODE_FIT
        mBinding.exoPlayer.setShutterBackgroundColor(R.color.transparent)
        play()
    }

    private fun play(){
        val uri = Uri.parse("")
        val exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT

        val item = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(item)
        exoPlayer.prepare()
    }
}