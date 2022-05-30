package com.example.owentime

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.adapter.HomePagerAdapter
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityMainBinding
import com.example.owentime.ui.HomeFragment
import com.example.owentime.ui.MineFragment
import com.example.owentime.ui.ProjectFragment
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.smtt.sdk.QbSdk

class MainActivity: BaseActivity(R.layout.activity_main) {
    private val mBinding by viewBinding(ActivityMainBinding::bind)
//    private val mAdapter by lazy {HomePagerAdapter(fragments,supportFragmentManager,lifecycle) }
    private var fragments:MutableList<Fragment> = mutableListOf()

    override fun initData() {

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
                        keyboardEnable(true)
                        statusBarDarkFont(true)
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
                        immersionBar {
                            statusBarColor(R.color.white)
                            keyboardEnable(true)
                            statusBarDarkFont(true)
                            fitsSystemWindows(true)
                        }
                    }
                    mBinding.homeVp.setCurrentItem(2,false)
                }
            }
            true
        }
        mBinding.bottomNav.selectedItemId=0
        initX5()
    }

    private fun initX5(){
        QbSdk.initX5Environment(application, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {

            }
        })
    }
}