package com.example.time_project

import android.content.pm.ActivityInfo
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.adapter.HomePagerAdapter
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityMainBinding
import com.example.time_project.ui.HomeFragment
import com.example.time_project.ui.MineFragment
import com.example.time_project.ui.ProjectFragment
import com.gyf.immersionbar.ktx.immersionBar
//import com.tencent.smtt.sdk.QbSdk
import kotlin.system.exitProcess

class MainActivity: BaseActivity(R.layout.activity_main) {
    private val mBinding by viewBinding(ActivityMainBinding::bind)
//    private val mAdapter by lazy {HomePagerAdapter(fragments,supportFragmentManager,lifecycle) }
    private var fragments:MutableList<Fragment> = mutableListOf()


    override fun initData() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        immersionBar {
            statusBarColor(R.color.FE9520)
            keyboardEnable(false)
            statusBarDarkFont(false)
            fitsSystemWindows(true)
        }
        fragments.add(HomeFragment.newInstance())
        fragments.add(ProjectFragment.newInstance())
//        fragments.add(PublicFragment.newInstance())
        fragments.add(MineFragment.newInstance())
        mBinding.homeVp.currentItem = 0
        mBinding.homeVp.adapter=HomePagerAdapter(fragments,supportFragmentManager,lifecycle)
        mBinding.homeVp.offscreenPageLimit= ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        mBinding.homeVp.isUserInputEnabled=false
        mBinding.bottomNav.itemIconTintList=null
        mBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    immersionBar {
                        statusBarColor(R.color.FE9520)
                        keyboardEnable(false)
                        statusBarDarkFont(false)
                        fitsSystemWindows(true)
                    }
                    mBinding.homeVp.setCurrentItem(0,false)
                }
                R.id.project -> {
                    immersionBar {
                        statusBarColor(R.color.white)
                        keyboardEnable(true)
                        statusBarDarkFont(true)
                        fitsSystemWindows(true)
                    }
                    mBinding.homeVp.setCurrentItem(1,false)
                }

                R.id.mine -> {
                    immersionBar {
                        statusBarColor(R.color.white)
                        keyboardEnable(true)
                        statusBarDarkFont(true)
                        fitsSystemWindows(true)
                    }
                    mBinding.homeVp.setCurrentItem(2,false)
                }
            }
            true
        }
        mBinding.bottomNav.selectedItemId=0
//        initX5()
    }

//    初始化腾讯x5
//    private fun initX5(){
//        QbSdk.initX5Environment(application, object : QbSdk.PreInitCallback {
//            override fun onCoreInitFinished() {
//
//            }
//
//            override fun onViewInitFinished(p0: Boolean) {
//
//            }
//        })
//        QbSdk.setDownloadWithoutWifi(true)
//    }


    private var exitTime=0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-exitTime>2000){
                toast("再按一次退出应用")
                exitTime = System.currentTimeMillis()
            }else{
                finish()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}