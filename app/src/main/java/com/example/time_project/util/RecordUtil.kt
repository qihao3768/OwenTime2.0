package com.example.time_project.util

import android.content.Context
import android.widget.Toast
import com.example.time_project.toast
import tech.oom.idealrecorder.IdealRecorder
import tech.oom.idealrecorder.IdealRecorder.RecordConfig
import tech.oom.idealrecorder.StatusListener
import java.io.File

object RecordUtil {
    private val idealRecorder: IdealRecorder? = null
    private val recordConfig: RecordConfig? = null
    private val files = ""

    /**
     * 准备录音 录音之前 先判断是否有相关权限
     */
    fun readyRecord(context: Context?) {}

    private val statusListener: StatusListener = object : StatusListener() {
        override fun onStartRecording() {}
        override fun onRecordData(data: ShortArray, length: Int) {
//            LogUtil.d("MainActivity", "current buffer size is $length")
        }

        override fun onVoiceVolume(volume: Int) {
            val myVolume = ((volume - 40) * 4).toDouble()
//            LogUtil.d("MainActivity", "current volume is $volume")
        }

        override fun onRecordError(code: Int, errorMsg: String) {
            // tips.setText("录音错误" + errorMsg);
        }

        override fun onFileSaveFailed(error: String) {
            toast("文件保存失败")
//            Toast.makeText(StartApp.getContext(), "文件保存失败", Toast.LENGTH_SHORT).show()
        }

        override fun onFileSaveSuccess(fileUri: String) {
            toast("文件保存成功")
//            Toast.makeText(StartApp.getContext(), "文件保存成功", Toast.LENGTH_SHORT).show()
        }

        override fun onStopRecording() {
            //tips.setText("录音结束");
        }
    }

    /**
     * 开始录音
     */
    fun record(
        statusListener: StatusListener?,
        idealRecorder: IdealRecorder,
        recordConfig: RecordConfig?,
        context: Context
    ) {
        //如果需要保存录音文件  设置好保存路径就会自动保存  也可以通过onRecordData 回调自己保存  不设置 不会保存录音
        val path = getSaveFilePath(context)
        //        Log.d("path",path);
        idealRecorder.setRecordFilePath(path)
        //        idealRecorder.setWavFormat(false);
        //设置录音配置 最长录音时长 以及音量回调的时间间隔
        idealRecorder.setRecordConfig(recordConfig).setMaxRecordTime(200000000)
            .setVolumeInterval(200)
        //设置录音时各种状态的监听
        idealRecorder.setStatusListener(statusListener)
        idealRecorder.start() //开始录音
    }

    /**
     * 开始录音
     */
    fun record(idealRecorder: IdealRecorder, recordConfig: RecordConfig?, context: Context) {
        //如果需要保存录音文件  设置好保存路径就会自动保存  也可以通过onRecordData 回调自己保存  不设置 不会保存录音
        val path = getSaveFilePath(context)
        //        Log.d("path",path);
        idealRecorder.setRecordFilePath(path)
        //        idealRecorder.setWavFormat(false);
        //设置录音配置 最长录音时长 以及音量回调的时间间隔
        idealRecorder.setRecordConfig(recordConfig).setMaxRecordTime(200000000)
            .setVolumeInterval(200)
        //设置录音时各种状态的监听
        idealRecorder.setStatusListener(statusListener)
        idealRecorder.start() //开始录音
    }

    /**
     * 获取文件保存路径
     *
     * @return
     */
    private fun getSaveFilePath(context: Context): String {
//        File file = new File(Environment.getExternalStorageDirectory(), "Audio");
//        String path = context.getFilesDir()+"/Audio";
//        String path = "/storage/emulated/0/";
        val path = context.getExternalFilesDir(null)!!.path
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        //        File wavFile = new File(file, "ideal.wav");/storage/emulated/0/12345.m4a
        val wavFile = File(file, "ideal.m4a")
        return wavFile.absolutePath
    }

    /**
     * 停止录音
     */
    fun stopRecord(context: Context, idealRecorder: IdealRecorder): String? {
        //停止录音
        idealRecorder.stop()
        return getSaveFilePath(context)
    }


}