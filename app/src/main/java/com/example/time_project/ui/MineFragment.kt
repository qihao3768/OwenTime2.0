package com.example.time_project.ui

import android.view.Gravity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.time_project.*
import com.example.time_project.base.BaseFragment
import com.example.time_project.base.BasePopWindow
import com.example.time_project.databinding.MineFragmentBinding
import com.example.time_project.util.IntentExtra.Companion.iBirthday
import com.example.time_project.util.IntentExtra.Companion.iHead
import com.example.time_project.util.IntentExtra.Companion.iSex
import com.example.time_project.util.IntentExtra.Companion.iSkip
import com.example.time_project.util.IntentExtra.Companion.iUserName
import com.example.time_project.vm.MineViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.shape.view.ShapeTextView
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import kotlin.random.Random

class MineFragment : BaseFragment(R.layout.mine_fragment) {

    companion object {
        fun newInstance() = MineFragment()

    }

    private val mViewModel by viewModels<MineViewModel>()
    private val mBinding by viewBinding(MineFragmentBinding::bind)
    private val mmkv=MMKV.defaultMMKV()
    private var mToken=""//token


    private var mUserName:String?=""//用户名
    private var mSex:Int=0//性别
    private var mBirth:String?=""//生日
    private var mHead:String?=""//头像

    private var zhujiao = BasePopWindow(this)
    private val refreshOb:Observer<String> = Observer {
        when(it){
            "login"->{
                getUser()
            }
            "loginout"->{
                mBinding.personalPhoto.load(R.drawable.logo)
                mBinding.personalName.text="登录/注册"
            }
        }

    }
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
        }
        mBinding.layoutMyorder.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                start(requireActivity(),OrderListActivity().javaClass,false)
            }
        })
        mBinding.layoutTuihuo.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {

            }
        })
        mBinding.layoutYouhui.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                start(requireActivity(),CouponActivity().javaClass,false)
            }
        })
        mBinding.layoutKefu.fastClick {
            showKefu("400-870-2880")
        }
        mBinding.layoutZhujiao.fastClick {
            showZhujiao("400-870-2880")
        }
        mBinding.layoutSetup.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                requireActivity().intent.iSkip=true
                requireActivity().intent.iUserName=mUserName
                requireActivity().intent.iSex=mSex
                requireActivity().intent.iBirthday=mBirth
                requireActivity().intent.iHead=mHead
                start(requireActivity(),SettingActivity().javaClass,requireActivity().intent)
            }
        })
        mBinding.layoutDub.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                start(requireActivity(),WorksActivity().javaClass,false)
            }
        })

        mBinding.personalPhoto.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                requireActivity().intent.iSkip=true
                requireActivity().intent.iUserName=mUserName
                requireActivity().intent.iSex=mSex
                requireActivity().intent.iBirthday=mBirth
                requireActivity().intent.iHead=mHead
                start(requireActivity(),PerfectActivity().javaClass,requireActivity().intent)
            }
        })

        mBinding.personalName.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                requireActivity().intent.iSkip=true
                requireActivity().intent.iUserName=mUserName
                requireActivity().intent.iSex=mSex
                requireActivity().intent.iBirthday=mBirth
                requireActivity().intent.iHead=mHead
                start(requireActivity(),PerfectActivity().javaClass,requireActivity().intent)
            }
        })



//        LiveEventBus.get<String>("logout").observe(this, logoutOb)
        LiveEventBus.get<String>("refresh").observe(this, refreshOb)
    }

    override fun onResume() {
        super.onResume()
        getUser()
    }

    /***
     * 获取用户信息
     */
    private fun getUser(){
        mToken=mmkv.decodeString("token")?:""
        if (mToken.isNotEmpty()){
            mViewModel.getUser(mToken).observe(requireActivity(), Observer {
                it?.run {
                    mBinding.personalPhoto.load(photo?:""){
                        placeholder(R.drawable.logo)
                            .error(R.drawable.logo)
                    }
                    mBinding.personalName.text= if (name.isNullOrBlank()){
                        "Owen".plus(Random.nextInt(1000,10000))
                    }else{
                        mUserName=name
                        mSex=sex?:0
                        mBirth=birthday?:""
                        mHead=photo?:""
                        name
                    }
                }
            })
        }else{
            mBinding.personalPhoto.load(R.drawable.logo)
            mBinding.personalName.text="登录/注册"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        LiveEventBus.get<String>("logout").removeObserver(logoutOb)
        LiveEventBus.get<String>("refresh").removeObserver(refreshOb)
    }

    private fun showZhujiao(content:String){
        val view=layoutInflater.inflate(R.layout.layout_zhujiao,null)
        view.findViewById<ShapeTextView>(R.id.content).text=content
        zhujiao.contentView=view
        zhujiao.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.CENTER)
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

    private fun showKefu(content:String){
        val view=layoutInflater.inflate(R.layout.layout_kefu,null)
        view.findViewById<ShapeTextView>(R.id.content).text=content
        zhujiao.contentView=view
        zhujiao.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.CENTER)
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
}