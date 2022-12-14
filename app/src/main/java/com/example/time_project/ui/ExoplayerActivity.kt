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
 * ????????????
 */
class ExoplayerActivity : BaseActivity(R.layout.activity_exoplayer) {

    private lateinit var dub_start: ImageView
    private lateinit var subTitle: String
    private lateinit var shareTitle: String
    private val mBinding by viewBinding(ActivityExoplayerBinding::bind)

    //    private var mUrl="https://owen-time-test.oss-cn-beijing.aliyuncs.com/courses/cou/1643348728_216a94a44ba39a712.mp4"
    private lateinit var exoPlayer: ExoPlayer
    private var isLock = true //????????????

    private lateinit var shareDialog: BasePopWindow//????????????
    private lateinit var shareBinding: LayoutShareBinding

    private lateinit var mShareAction: ShareAction//??????

    private val viewModel by viewModels<OwenViewModel>()

    private var postion: Long = 0L//??????????????????

    private lateinit var idealRecorder: IdealRecorder
    private lateinit var recordConfig: RecordConfig

    //?????????????????????
    private val permissions = listOf(
        Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var popWindow //??????????????????
            : BasePopWindow
    private lateinit var bar: ContentLoadingProgressBar
    private lateinit var tv_bar_start: TextView
    private lateinit var tv_bar_end: TextView
    private lateinit var progressTitle: TextView

    private lateinit var save: ShapeTextView
    private lateinit var reset: ShapeTextView
    private lateinit var exobinding: LayoutCustomExo2Binding
    private val mViewModel by viewModels<OwenViewModel>()//??????????????????vm

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

        //????????????????????????
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
        //??????
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

        //????????????
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


        //?????????????????????
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
                    scope.showRequestReasonDialog(deniedList, "????????????????????????????????????????????????", "??????", "??????")
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        exoPlayer.play()
                        it.visibility=View.GONE
                        startRecord()
                    } else {
                        it.visibility=View.GONE
                        toast("??????????????????????????????????????????????????????????????????")
                    }
                }

        }

        //?????????????????????
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
        //??????????????????
        //         ??????courseDub??????????????????????????????????????????????????????????????????
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
                            toast("????????????????????????????????????")
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
     * ????????????
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
     * ??????
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
     * ???????????????
     * platform ?????? ??????????????????
     */
    private fun shareWX(platform: SHARE_MEDIA) {
        val web: UMWeb = UMWeb(mmkv.decodeString("Shareurl"))
        web.title = "????"+shareTitle //??????
        val umImage = UMImage(this,intent.shareImage)
        web.setThumb(umImage) //?????????
        web.description = "????"+subTitle //??????
        mShareAction.setPlatform(platform)
            .withMedia(web)
            .setCallback(object : UMShareListener {
                override fun onStart(p0: SHARE_MEDIA) {

                }

                override fun onResult(p0: SHARE_MEDIA) {
                    if (p0.name == "WEIXIN_FAVORITE") {
                        toast("????????????")

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
                            toast("????????????")
                        }
                    }
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                    if (p0 != SHARE_MEDIA.MORE && p0 != SHARE_MEDIA.SMS && p0 != SHARE_MEDIA.EMAIL && p0 != SHARE_MEDIA.FLICKR && p0 != SHARE_MEDIA.FOURSQUARE && p0 != SHARE_MEDIA.TUMBLR && p0 != SHARE_MEDIA.POCKET && p0 != SHARE_MEDIA.PINTEREST && p0 != SHARE_MEDIA.INSTAGRAM && p0 != SHARE_MEDIA.GOOGLEPLUS && p0 != SHARE_MEDIA.YNOTE && p0 != SHARE_MEDIA.EVERNOTE) {
                        toast("????????????")
                    }
                }

                override fun onCancel(p0: SHARE_MEDIA?) {
                    toast("????????????")
                }
            }).share()
    }

    /****
     * ???????????????????????????
     */
    private fun registerVideoListener(listener: Player.Listener) {
        exoPlayer.addListener(listener)
    }

    //??????????????????
    private val playerListener: Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_IDLE -> {}
                Player.STATE_BUFFERING -> {}
                Player.STATE_READY -> {

                }
                Player.STATE_ENDED -> {
                    if (mediaItemId.equals("1")) {
                        //????????????
                        if (!intent.courseDub.isNullOrBlank() || courseDub.isNotBlank()) {
                           // toast("???????????????????????????...")
                            RecordUtil.stopRecord(this@ExoplayerActivity, idealRecorder)
                        }else{
                            share()
                        }

                    }

                }
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {

            if (playWhenReady) {
                //??????????????????????????????????????????????????????????????????????????????????????????????????????
                storageRecord("0")
                //??????
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
                    toast("????????????")
                }
                PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                    toast("?????????????????????")
                }
                PlaybackException.ERROR_CODE_IO_NO_PERMISSION -> {
                    toast("????????????")
                }
                else -> {
                    toast("????????????")
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
     * ??????????????????
     * iproductId ??????id
     * courseId ??????id
     * time ??????
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
                                //????????????????????? ????????????
                            }
                            401 -> {
                                toast("???????????????????????????????????????")
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
                toast("????????????????????????")
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
     * ??????
     */
    private fun doPunch(product: String, course: String) {
        viewModel.doPunch(product, course).observe(this, Observer {
            it?.run {
                when (code) {
                    1000 -> {

                    }
                    401 -> {
                        toast("????????????????????????????????????")
                    }
                    else -> {
                        toast(message.toString())
                    }
                }
            }
        })
    }

    /***
     * ????????????
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
            // tips.setText("????????????" + errorMsg);
        }

        override fun onFileSaveFailed(error: String) {
            toast("??????????????????")

        }

        override fun onFileSaveSuccess(fileUri: String) {
            toast("??????????????????")
            var url: String = ""
            if (intent.courseDub.equals("")) {
                url = courseDub
            } else {
                url = intent.courseDub.toString()
            }
            runFFmpegRxJava(url, fileUri)
        }

        override fun onStopRecording() {
            //tips.setText("????????????");
        }
    }


    //????????????
    private fun startRecord() {
        RecordUtil.record(statusListener, idealRecorder, recordConfig, this@ExoplayerActivity)
       // toast("????????????...")
    }

    /***
     * ???????????????ffffff
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
        val text1 = "ffmpeg -i " + url + " -an -vcodec copy " + noAideoFile.path //??????????????????
        val commands1 = text1.split(" ").toTypedArray()

//        String text= "ffmpeg -i "+url+" -i "+path+" -filter_complex amix=inputs=2 "+resultFile.getPath();//???????????????
        val text = "ffmpeg -i " + noAideoFile.path + " -i " + path + " " + resultFile.path //???????????????
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
        progressTitle.text = "??????????????????,???????????????..."
        popWindow.showPopupWindow()
        RxFFmpegInvoke.getInstance()
            .runCommandRxJava(commons1)
            .subscribe(object : RxFFmpegSubscriber() {
                override fun onFinish() {
                    toast("??????????????????")
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
                    toast("?????????")
                }

                override fun onError(message: String) {
                    toast(message)
                }
            })
    }

    private fun heCheng(commons: Array<String>) {
        progressTitle.text = "????????????????????????????????????..."
        if (!popWindow.isShowing) {
            popWindow.showPopupWindow()
        }

        //????????????FFmpeg??????ffffffffefefefefefehhh
        RxFFmpegInvoke.getInstance()
            .runCommandRxJava(commons)
            .subscribe(object : RxFFmpegSubscriber() {
                override fun onFinish() {
                    toast("????????????")
                    popWindow.dismiss()

                    //?????????????????????????????????????????????
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
                    toast("?????????")
                }

                override fun onError(message: String) {
                    toast(message)
                }
            })
    }

    /***
     * ????????????
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
                        toast("????????????")
                    }
                    401 -> {
                        toast("??????????????????")
                    }
                    else -> {
                        toast(message.toString())
                    }
                }
            }
        }
    }

    /***
     * ??????
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