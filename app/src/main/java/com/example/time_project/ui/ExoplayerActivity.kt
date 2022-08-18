package com.example.time_project.ui

import android.Manifest
import android.content.pm.ActivityInfo
import android.media.AudioFormat
import android.media.MediaRecorder
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.time_project.*
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.bean.mine.DubGroupModel
import com.example.time_project.bean.order.Course
import com.example.time_project.databinding.ActivityExoplayerBinding
import com.example.time_project.databinding.LayoutCustomExo2Binding
import com.example.time_project.databinding.LayoutShareBinding
import com.example.time_project.util.IntentExtra.Companion.courseDub
import com.example.time_project.util.IntentExtra.Companion.courseId
import com.example.time_project.util.IntentExtra.Companion.courseTime
import com.example.time_project.util.IntentExtra.Companion.courseTitle
import com.example.time_project.util.IntentExtra.Companion.courseUrl
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.position
import com.example.time_project.util.IntentExtra.Companion.shareImage
import com.example.time_project.util.RecordUtil
import com.example.time_project.util.TextViewLinesUtil
import com.example.time_project.vm.OwenViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.PositionInfo
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.shape.view.ShapeButton
import com.hjq.shape.view.ShapeTextView
import com.permissionx.guolindev.PermissionX
import com.tencent.mmkv.MMKV
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import io.microshow.rxffmpeg.RxFFmpegInvoke
import io.microshow.rxffmpeg.RxFFmpegSubscriber
import kotlinx.coroutines.launch
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import tech.oom.idealrecorder.IdealRecorder
import tech.oom.idealrecorder.IdealRecorder.RecordConfig
import tech.oom.idealrecorder.StatusListener
import java.io.File
import java.util.ArrayList


/***
 * 视频播放
 */
class ExoplayerActivity : BaseActivity(R.layout.activity_exoplayer) {

    private lateinit var dub_start: ImageView
    private lateinit var subTitle: String
    private lateinit var shareTitle: String
    private val mBinding by viewBinding(ActivityExoplayerBinding::bind)

    //    private var mUrl="https://owen-time-test.oss-cn-beijing.aliyuncs.com/courses/cou/1643348728_216a94a44ba39a712.mp4"
    private lateinit var exoPlayer: ExoPlayer
    private var isLock = true //是否锁屏

    private lateinit var shareDialog: BasePopWindow//分享面板
    private lateinit var shareBinding: LayoutShareBinding

    private lateinit var mShareAction: ShareAction//分享

    private val viewModel by viewModels<OwenViewModel>()

    private var postion: Long = 0L//视频播放进度

    private lateinit var idealRecorder: IdealRecorder
    private lateinit var recordConfig: RecordConfig

    //录音用到的权限
    private val permissions = listOf(
        Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var popWindow //合成的进度条
            : BasePopWindow
    private lateinit var bar: ContentLoadingProgressBar
    private lateinit var tv_bar_start: TextView
    private lateinit var tv_bar_end: TextView
    private lateinit var progressTitle: TextView

    private lateinit var save: ShapeTextView
    private lateinit var reset: ShapeTextView
    private lateinit var exobinding: LayoutCustomExo2Binding
    private val mViewModel by viewModels<OwenViewModel>()//获取播放列表vm

    var courseId: String = ""
    var productId: String = ""
    var courseDub: String = ""
    var mediaItemId: String = ""
    val mmkv: MMKV = MMKV.defaultMMKV()

    override fun initData() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        immersionBar {
            titleBar(mBinding.exoTitle, false)
            transparentBar()
            hideBar(BarHide.FLAG_HIDE_BAR)
            init()
        }
        getShare()
        //        IdealRecorder.getInstance().init(this);
        idealRecorder = IdealRecorder.getInstance()
        idealRecorder.init(this)
        recordConfig = RecordConfig(
            MediaRecorder.AudioSource.MIC,
            48000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT

        //初始化播放器控件
        val view = layoutInflater.inflate(R.layout.layout_custom_exo2, null, false)
    /*    view.findViewById<ImageView>(R.id.exo_pause).setOnClickListener {
            val postion = exoPlayer.contentPosition / 1000
            storageRecord(postion.toString())
        }*/
      /*   view.findViewById<ImageView>(R.id.exo_play)
             .setImageDrawable(resources.getDrawable(R.drawable.alipay_icon))*/
        exobinding = LayoutCustomExo2Binding.bind(view)
        /*  exobinding.exoPause.setOnClickListener {
              val postion = exoPlayer.contentPosition / 1000
              storageRecord(postion.toString())
          }*/
        val include = layoutInflater.inflate(R.layout.include_exo3, null, true)
        //分享
        val share = include.findViewById<ImageView>(R.id.video_share)
        share.fastClick {
            share()
        }
        val back = include.findViewById<ImageView>(R.id.video_back)
        back.fastClick {
            stop()
            removeActivity()
        }
         dub_start = include.findViewById<ImageView>(R.id.iv_dub_start)

        //保存配音
        save = include.findViewById<ShapeTextView>(R.id.tv_save)
        save.fastClick {
            save()
        }

        reset = include.findViewById<ShapeTextView>(R.id.btn_reset)
        save.fastClick {
            save()
        }

        if (intent.position == -1) {
            initUrlSource()
        } else {
            initUrlList()
        }


        //投屏，暂时不做
        /*   val forscreen=include.findViewById<TextView>(R.id.tv_forscreen)
           forscreen.setOnClickListener {

           }*/
        mBinding.exoPlayer.overlayFrameLayout?.addView(include)
        mBinding.exoPlayer.keepScreenOn = true
        mBinding.exoPlayer.controllerAutoShow = true
        mBinding.exoPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        mBinding.exoTitle.title = intent.courseTitle
        exoPlayer.prepare()
        registerVideoListener(playerListener)
        exoPlayer.seekTo(intent.courseTime.toLong())
        mBinding.exoPlayer.player = exoPlayer

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

        if (isLock) {
            mBinding.exoPlayer.useController=false
            mBinding.ivUnlock.setBackgroundResource(R.drawable.icon_player_lock)
        }

        if (!intent.courseDub.isNullOrBlank() || courseDub.isNotBlank()) {
            if (exoPlayer.isPlaying){
                dub_start.visibility=View.GONE
            }else{
                dub_start.visibility=View.VISIBLE
            }
        }
        dub_start.setOnClickListener {

            PermissionX.init(this).permissions(permissions)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(deniedList, "拒绝权限可能会导致该功能无法使用", "授权", "拒绝")
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        exoPlayer.play()
                        it.visibility=View.GONE
                        startRecord()
                    } else {
                        toast("您拒绝了相关权限可能会对您的正常使用造成影响")
                    }
                }

        }

        //初始化分享面板
        val panel = layoutInflater.inflate(R.layout.layout_share, null)
        shareBinding = LayoutShareBinding.bind(panel)
        exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        if (intent.courseDub.isNullOrBlank()) {
            exoPlayer.play()
        }

    }


    private fun getShare() {
        viewModel.getShare("2").observe(this, Observer {
            subTitle = it.data?.description.toString()
            shareTitle= intent.courseTitle.toString()
        })
    }


    private fun initUrlSource() {
        //初始化播放器
        //         如果courseDub不为空，说明这节视频需要配音，后续走配音流程
        val uri: Uri
        if (!intent.courseDub.isNullOrBlank()) {
            uri = Uri.parse(intent.courseDub)
            mmkv.encode("Shareurl",intent.courseDub.toString())
        } else {
            uri = Uri.parse(intent.courseUrl)
            mmkv.encode("Shareurl",intent.courseUrl.toString())
        }
        val item = MediaItem.fromUri(uri)
        mediaItemId = "1"
        exoPlayer.setMediaItem(item)

    }

    private fun initUrlList() {
        mViewModel.getCourse(intent.iproductId.toString()).observe(
            this, Observer {
                it?.run {
                    when (code) {
                        1000 -> {
                            val body: Course? = data
                            val mmd: MediaMetadata.Builder = MediaMetadata.Builder()
                            val mediaItem: MediaItem.Builder = MediaItem.Builder()
                            for (i in intent.position until body?.course?.size!!) {
                                val uri = Uri.parse(body.course[i].url)
                                mediaItem.setUri(uri)
                                if (i == intent.position) {
                                    if (intent.courseDub.isNullOrBlank()) {
                                        mediaItem.setMediaId("0")
                                    } else {
                                        mediaItem.setMediaId("1")
                                    }

                                } else {
                                    mediaItem.setMediaId("1")
                                }
                                mmd.setTitle("${body.course[i].id}")
                                mmd.setSubtitle("${body.course[i].productId}")
                                mmd.setStation(body.course[i].dubCourse ?: "")
                                mmd.setAlbumTitle(body.course[i].url?:"")
                                mediaItem.setMediaMetadata(mmd.build())
                                exoPlayer.addMediaItem(mediaItem.build())
                                mmkv.encode("courseid", body.course[i].id.toString())
                            }
                        }
                        401 -> {
                            toast("登录状态失效，请重新登录")
                        }
                        else -> {
                            toast(message.toString())
                        }
                    }
                }
            }
        )
    }


    /***
     * 停止播放
     */
    private fun stop() {
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying) {
                exoPlayer.stop()
                exoPlayer.release()
                val postion = exoPlayer.contentPosition / 1000
                storageRecord(postion.toString())
            }
        }
    }

    private fun pause() {
        mBinding.exoPlayer.run {
            if (exoPlayer.isPlaying) {
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
    private fun share() {
        mShareAction = ShareAction(this)
        shareDialog = BasePopWindow(this)
        shareDialog.contentView = shareBinding.root
        shareBinding.shareClose.setOnClickListener {

            if (!productId.isNullOrBlank() && !courseId.isNullOrBlank()) {
                if (productId != "null" && courseId != "null") {
                    doPunch(productId, courseId)
                }
            }
            shareDialog.dismiss()
            if (courseDub.isBlank()) {
                exoPlayer.play()
            }
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
    private fun shareWX(platform: SHARE_MEDIA) {
        val web: UMWeb = UMWeb(mmkv.decodeString("Shareurl"))
        web.title = "👉"+shareTitle //标题
        val umImage = UMImage(this,intent.shareImage)
        web.setThumb(umImage) //缩略图
        web.description = "🏆"+subTitle //描述
        mShareAction.setPlatform(platform)
            .withMedia(web)
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
                Player.STATE_READY -> {

                }
                Player.STATE_ENDED -> {
                    if (mediaItemId.equals("1")) {
                        //停止配音
                        if (!intent.courseDub.isNullOrBlank() || courseDub.isNotBlank()) {
                            toast("停止配音，开始合成...")
                            RecordUtil.stopRecord(this@ExoplayerActivity, idealRecorder)
                        }
                        share()
                    }

                }
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {

            if (playWhenReady) {
                //进入到播放页面的时候先保存一次播放记录，目的是防止应用大退时无法保存
                storageRecord("0")
                //配音
               /* if (!intent.courseDub.isNullOrBlank() || courseDub.isNotBlank()) {
                    getPermission()
                }*/

            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            productId = mediaItem?.mediaMetadata?.subtitle.toString()
            courseId = mediaItem?.mediaMetadata?.title.toString()
            courseDub = mediaItem?.mediaMetadata?.station.toString()
            mediaItemId = mediaItem?.mediaId.toString()
            if (!mediaItem?.mediaMetadata?.albumTitle.isNullOrBlank()){
                mmkv.encode("Shareurl",mediaItem?.mediaMetadata?.albumTitle.toString())
            }


            if (mediaItemId.equals("1")) {
                mBinding.exoPlayer.useController=false
                exoPlayer.pause()
                isLock=true
                mBinding.ivUnlock.setBackgroundResource(R.drawable.icon_player_lock)

                if (intent.courseDub.isNullOrBlank()) {
                    share()
                }
                if (!intent.courseDub.isNullOrBlank() || courseDub.isNotBlank()) {
                    dub_start.visibility=View.VISIBLE
                }

            }

        }


        override fun onTimelineChanged(timeline: Timeline, reason: Int) {

        }


        override fun onPlayerError(error: PlaybackException) {
            when (error.errorCode) {
                PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS -> {
                    toast("播放失败")
                }
                PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                    toast("找不到视频文件")
                }
                PlaybackException.ERROR_CODE_IO_NO_PERMISSION -> {
                    toast("缺少权限")
                }
                else -> {
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
    private fun storageRecord(time: String) {
        viewModel.storageRecord(intent.iproductId.toString(), intent.courseId.toString(), time)
            .observe(this@ExoplayerActivity,
                Observer {
                    it?.run {
                        when (code) {
                            1000 -> {

                            }
                            1001 -> {
                                //我的作品不记录 报错忽略
                            }
                            401 -> {
                                toast("登录状态已失效，请重新登录")
                                start(this@ExoplayerActivity, LoginActivity().javaClass, true)
                            }
                            else -> {
                                toast(message.toString())
                            }
                        }
                    }
                })
    }

    private var exitTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次退出播放")
                exitTime = System.currentTimeMillis()
            } else {
                stop()
                removeActivity()
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
    private fun doPunch(product: String, course: String) {
        viewModel.doPunch(product, course).observe(this, Observer {
            it?.run {
                when (code) {
                    1000 -> {

                    }
                    401 -> {
                        toast("登录状态失效，请重新登录")
                    }
                    else -> {
                        toast(message.toString())
                    }
                }
            }
        })
    }

    /***
     * 录音回调
     */
    private val statusListener: StatusListener = object : StatusListener() {
        override fun onStartRecording() {}
        override fun onRecordData(data: ShortArray, length: Int) {
//            tech.oom.idealrecorder.utils.Log.d("MainActivity", "current buffer size is " + length);
        }

        override fun onVoiceVolume(volume: Int) {
            val myVolume = ((volume - 40) * 4).toDouble()
            //            tech.oom.idealrecorder.utils.Log.d("MainActivity", "current volume is " + volume);
        }

        override fun onRecordError(code: Int, errorMsg: String) {
            // tips.setText("录音错误" + errorMsg);
        }

        override fun onFileSaveFailed(error: String) {
            toast("文件保存失败")

        }

        override fun onFileSaveSuccess(fileUri: String) {
            toast("文件保存成功")
            var url: String = ""
            if (intent.courseDub.equals("")) {
                url = courseDub
            } else {
                url = intent.courseDub.toString()
            }
            runFFmpegRxJava(url, fileUri)
        }

        override fun onStopRecording() {
            //tips.setText("录音结束");
        }
    }


    //开始录音
    private fun startRecord() {
        RecordUtil.record(statusListener, idealRecorder, recordConfig, this@ExoplayerActivity)
       // toast("准备配音...")
    }

    /***
     * 合并音视频ffffff
     */
    private fun runFFmpegRxJava(url: String, path: String) {
        val resultFile = File(getExternalFilesDir(null), "/result.mp4")
        if (resultFile.exists()) {
            resultFile.delete()
        }

        val noAideoFile = File(getExternalFilesDir(null), "/noAideo.mp4")
        if (noAideoFile.exists()) {
            noAideoFile.delete()
        }
        val text1 = "ffmpeg -i " + url + " -an -vcodec copy " + noAideoFile.path //只保留视频流
        val commands1 = text1.split(" ").toTypedArray()

//        String text= "ffmpeg -i "+url+" -i "+path+" -filter_complex amix=inputs=2 "+resultFile.getPath();//音视频合成
        val text = "ffmpeg -i " + noAideoFile.path + " -i " + path + " " + resultFile.path //音视频合成
        val commands2 = text.split(" ").toTypedArray()
        getVideo(commands1, commands2)
    }

    private fun getVideo(commons1: Array<String>, commons2: Array<String>) {
        popWindow = BasePopWindow(this)
        popWindow.setContentView(R.layout.layout_synthetic_progress)
        popWindow.setPopupGravity(Gravity.CENTER).setOutSideDismiss(false).isOutSideTouchable =
            false
        popWindow.setBackPressEnable(false)
        bar = popWindow.contentView.findViewById(R.id.hecheng_pb)
        progressTitle = popWindow.contentView.findViewById(R.id.tv_progress_title)
        tv_bar_end = popWindow.contentView.findViewById(R.id.tv_bar_end)
        tv_bar_start = popWindow.contentView.findViewById(R.id.tv_bar_start)
        progressTitle.text = "正在提取视频,请不要退出..."
        popWindow.showPopupWindow()
        RxFFmpegInvoke.getInstance()
            .runCommandRxJava(commons1)
            .subscribe(object : RxFFmpegSubscriber() {
                override fun onFinish() {
                    toast("视频提取成功")
                    heCheng(commons2)
                }

                override fun onProgress(progress: Int, progressTime: Long) {
                    Log.d("progress1", "" + progress)
                    Handler(mainLooper).post {
                        bar.progress = progress
                        tv_bar_end.text = "$progress/100"
                        tv_bar_start.text = "$progress%"
                    }
                }

                override fun onCancel() {
                    toast("已取消")
                }

                override fun onError(message: String) {
                    toast(message)
                }
            })
    }

    private fun heCheng(commons: Array<String>) {
        progressTitle.text = "正在合成视频，请不要退出..."
        if (!popWindow.isShowing) {
            popWindow.showPopupWindow()
        }

        //开始执行FFmpeg命令ffffffffefefefefefehhh
        RxFFmpegInvoke.getInstance()
            .runCommandRxJava(commons)
            .subscribe(object : RxFFmpegSubscriber() {
                override fun onFinish() {
                    toast("合成成功")
                    popWindow.dismiss()

                    //合成成功之后自动转入到预览页面
                    showPreivew()
                }

                override fun onProgress(progress: Int, progressTime: Long) {
//                        Log.d("progress1",""+progress);
//                        Log.d("progress2",""+progressTime);
                    Handler(mainLooper).post {
                        bar.progress = progress
                        tv_bar_end.text = "$progress/100"
                        tv_bar_start.text = "$progress%"
                    }
                }

                override fun onCancel() {
                    toast("已取消")
                }

                override fun onError(message: String) {
                    toast(message)
                }
            })
    }

    /***
     * 保存配音
     */
    private fun save() {
        pause()
        showLoading()
        viewModel.storageDub(
            courseId = mmkv.decodeString("courseid")!!,
            url = getExternalFilesDir(null).toString() + "/result.mp4"
        ).observe(
            this
        ) {
            hideLoading()
            it?.run {
                when (code) {
                    1000 -> {
                        toast("上传成功")
                    }
                    401 -> {
                        toast("登录状态失效")
                    }
                    else -> {
                        toast(message.toString())
                    }
                }
            }
        }
    }

    /***
     * 预览
     */
    private fun showPreivew() {
        exoPlayer.clearMediaItems()
        mBinding.exoPlayer.controllerAutoShow = false
        exoPlayer.removeListener(playerListener)
        exoPlayer.seekTo(0L)
        val local = getExternalFilesDir(null).toString() + "/result.mp4"
        val uri1 = Uri.parse(local)
        val item1 = MediaItem.fromUri(uri1)
        exoPlayer.setMediaItem(item1)
        exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        exoPlayer.prepare()
        exoPlayer.pause()
        exobinding.exoPlay.visibility = View.GONE
        exobinding.exoPause.visibility = View.GONE
        save.visibility = View.VISIBLE
    }

}