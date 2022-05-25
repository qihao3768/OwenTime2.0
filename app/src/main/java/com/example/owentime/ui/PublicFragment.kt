package com.example.owentime.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.WxArticlePagerAdapter
import com.example.owentime.base.BaseFragment
import com.example.owentime.databinding.PublicFragmentBinding
import com.example.owentime.vm.PublicViewModel

class PublicFragment : BaseFragment(R.layout.public_fragment) {

    companion object {
        fun newInstance() = PublicFragment()
    }

    private val mViewModel by viewModels<PublicViewModel>()
    private val mBinding by viewBinding(PublicFragmentBinding::bind)
    private val mFragments = mutableListOf<Fragment>()
    private val mTabs = arrayListOf<String>()

    override fun initData() {
        mViewModel.getWxArticleTree().observe(this, Observer {
            it.run {
                forEach {_wxArticle->
                    mTabs.add(_wxArticle.name)
                    val fragment=WxArticleFragment.newInstance(_wxArticle.id)
                    mFragments.add(fragment)
                }
            }

            val adapter=WxArticlePagerAdapter(mFragments,childFragmentManager,lifecycle)
            mBinding.publicVp.adapter=adapter
            mBinding.publicVp.offscreenPageLimit=ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            mBinding.publicTab.setViewPager2(mBinding.publicVp,mTabs)
            mBinding.publicTab.currentTab=0
        })

    }
}