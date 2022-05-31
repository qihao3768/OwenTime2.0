package com.example.owentime.ui

import android.Manifest
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.*
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityPerfectBinding
import com.example.owentime.util.CoilEngine
import com.example.owentime.util.DateUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.permissionx.guolindev.PermissionX
import com.tencent.mmkv.MMKV
import razerdp.basepopup.QuickPopupBuilder
import razerdp.basepopup.QuickPopupConfig
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import java.text.SimpleDateFormat
import java.util.*


class PerfectActivity : BaseActivity(R.layout.activity_perfect) {
    private val mBinding by viewBinding(ActivityPerfectBinding::bind)

    //    private val mViewModel by viewModels<LoginViewModel>()
    private val permissions = listOf(Manifest.permission.CAMERA)
    private lateinit var mmkv: MMKV

    override fun initData() {
        mmkv = MMKV.defaultMMKV()

        immersionBar {
            statusBarColor(R.color.FE9520)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.tvSkip.setOnClickListener {
            finish()
        }
        mBinding.ivHead.setOnClickListener {
            getPermission()
        }
        mBinding.tvHis.setOnClickListener {
            showTimeDialog()
        }
        mBinding.btnSave.setOnClickListener {
            // TODO:  调用更新用户信息接口
            mmkv.encode("islogin",true)
            start(this@PerfectActivity,MainActivity().javaClass,true)
        }
    }

    private fun getPermission() {
        PermissionX.init(this).permissions(permissions)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    showDialog()
                } else {
                    toast("These permissions are denied: $deniedList")
                }
            }
    }
    //选择拍照或者相册
    private fun showDialog(){
        QuickPopupBuilder.with(this).contentView(R.layout.dialog_photo)
            .config(
                QuickPopupConfig().gravity(Gravity.BOTTOM)
                    .withClick(R.id.tv_album,{
                        selectPic()
                    },true)
                    .withClick(R.id.tv_cancel,{

                    },true)
                    .fadeInAndOut(true)
                    .outSideDismiss(true)
                    .backpressEnable(true)
                    .autoLocated(false)
                    .withShowAnimation(
                        AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM)
                            .toShow()
                    )
                    .withDismissAnimation(
                        AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM)
                            .toDismiss()
                    )
            ).show()

    }
    //选择头像
    private fun selectPic(){
        PictureSelector.create(this).openGallery(SelectMimeType.ofImage()).setImageEngine(CoilEngine()).setSelectionMode(1).forResult(
            object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.run {
                        val path=result[0].path
                        mBinding.ivHead.load(path)
                    }

                }

                override fun onCancel() {

                }
            })
    }

    private fun showTimeDialog(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)

        val date = Calendar.getInstance()
        date[2000, 5] = 1
        val date2 = Calendar.getInstance()
        date2[2020, 5] = 1
        val popup =
            TimePickerPopup(this) //                        .setDefaultDate(date)  //设置默认选中日期
                //                        .setYearRange(1990, 1999) //设置年份范围
                //                        .setDateRange(date, date2) //设置日期范围
                .setTimePickerListener(object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                        //时间改变
                    }

                    override fun onTimeConfirm(date: Date, view: View?) {
                        //点击确认时间
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val birthday = dateFormat.format(date)
                       mBinding.tvHis.text= birthday
                    }
                })

        XPopup.Builder(this)
            .asCustom(popup)
            .show()
    }
}