package com.example.time_project.ui


import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.time_project.R
import com.example.time_project.base.BaseFragment
import com.example.time_project.databinding.FragmentHomeBinding
import com.example.time_project.start
import com.example.time_project.util.IntentExtra.Companion.iBirthday
import com.example.time_project.util.IntentExtra.Companion.iHead
import com.example.time_project.util.IntentExtra.Companion.iSex
import com.example.time_project.util.IntentExtra.Companion.iSkip
import com.example.time_project.util.IntentExtra.Companion.iUserName
import com.example.time_project.vm.MineViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tencent.mmkv.MMKV


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val mmkv: MMKV = MMKV.defaultMMKV()
    private val mViewModel by viewModels<MineViewModel>()
    private val mBinding by viewBinding(FragmentHomeBinding::bind)
    private var fragments = mutableListOf<Fragment>()
    private var titles = mutableListOf<String>()
    private var mUserName: String? = ""//用户名
    private var mSex: Int = 0//性别
    private var mBirth: String? = ""//生日
    private var mHead: String? = ""//头像

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initData() {
        titles.add("推荐")
        titles.add("阅读动画")
        titles.add("游戏王国")

        fragments.add(RecommendFragment.newInstance())
        fragments.add(ReadFragment.newInstance())
        fragments.add(GameFragment.newInstance())

        mBinding.viewpager2.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewpager2.adapter=object : FragmentStateAdapter(requireFragmentManager(),this.lifecycle){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
               return fragments[position]
            }
        }
        mBinding.tablayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_title)
                textView.textSize=20f
                textView.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_title)
                textView.textSize=16f
                textView.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        TabLayoutMediator(mBinding.tablayout,mBinding.viewpager2){tab, position ->
            tab.setCustomView(R.layout.item_tablayout)
            val textView: TextView = tab.customView!!.findViewById(R.id.tab_title)
            textView.text = titles[position]
        }.attach()

        mBinding.ivHomeHead.setOnClickListener {
            //判断是否已经登录，如果登录，跳转修改用户信息，否则跳转登录
            val target = if (mmkv.decodeString("token").isNullOrBlank()) {
                LoginActivity()
            } else {
                requireActivity().intent.iSkip = true
                requireActivity().intent.iUserName = mUserName
                requireActivity().intent.iSex = mSex
                requireActivity().intent.iBirthday = mBirth
                requireActivity().intent.iHead = mHead
                PerfectActivity()
            }
            start(requireActivity(), target.javaClass, requireActivity().intent)
        }

        mBinding.ivSearch.setOnClickListener {
            start(requireActivity(),SearchActivity().javaClass,false)
        }
    }


    override fun onResume() {
        super.onResume()
        val token = mmkv.decodeString("token")
        if (!token.isNullOrBlank()) {
            getUser(token)
        }
    }

    private fun getUser(mToken: String) {
        mViewModel.getUser(mToken).observe(requireActivity(), Observer {
            it?.run {
                mBinding.ivHomeHead.load(photo ?: ""){
                    placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                }

                mUserName = name ?: ""
                mSex = sex ?: 0
                mBirth = birthday ?: ""
                mHead = photo ?: ""
            }
        })


    }

}