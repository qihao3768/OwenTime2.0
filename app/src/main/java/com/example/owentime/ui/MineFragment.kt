package com.example.owentime.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.TodoListener
import com.example.owentime.base.BaseFragment
import com.example.owentime.checkLogin
import com.example.owentime.databinding.MineFragmentBinding
import com.example.owentime.start
import com.example.owentime.vm.MineViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class MineFragment : BaseFragment(R.layout.mine_fragment) {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val mViewModel by viewModels<MineViewModel>()
    private val mBinding by viewBinding(MineFragmentBinding::bind)
    private val mmkv=MMKV.defaultMMKV()


    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
//            navigationBarColor(R.color.colorPrimary)
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

            }
        })
        mBinding.layoutKefu.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {

            }
        })
        mBinding.layoutZhujiao.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {

            }
        })
        mBinding.layoutSetup.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                start(requireActivity(),SettingActivity().javaClass,false)
            }
        })
        mBinding.layoutDub.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {
                start(requireActivity(),WorksActivity().javaClass,false)
            }
        })
        mBinding.personalPhoto.checkLogin(requireActivity(), object : TodoListener {
            override fun todo() {

            }
        })
    }

//    private fun user(){
//        mViewModel.user().observe(requireActivity(), Observer {
//            it?.run {
//                mBinding.tvUser.text=userInfo.nickname
//                mUserInfo=userInfo
//            }
//        })
//    }
}