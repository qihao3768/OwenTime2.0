package com.example.owentime.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.ArticleAdapter
import com.example.owentime.adapter.ProjectPagerAdapter
import com.example.owentime.base.BaseFragment
import com.example.owentime.databinding.ProjectFragmentBinding
import com.example.owentime.start
import com.example.owentime.vm.ProjectViewModel
import com.example.owentime.web.WebActivity
import kotlinx.coroutines.launch

class ProjectFragment : BaseFragment(R.layout.project_fragment) {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private val viewModel by viewModels<ProjectViewModel>()
    private val mBinding by viewBinding(ProjectFragmentBinding::bind)
    private val mFragmens= mutableListOf<Fragment>()
    private lateinit var mArticleAdapter:ArticleAdapter

    override fun initData() {
        initArticle()
//        viewModel.getProjectTree().observe(this, Observer {
//            val tabs= arrayListOf<String>()
//            it.run {
//                forEach { _project->
//                    tabs.add(_project.name)
//                    val subFragment = ProjectSubFragment.newInstance(_project.id)
//                    mFragmens.add(subFragment)
//                }
//            }
//            val adapter=ProjectPagerAdapter(mFragmens,childFragmentManager,lifecycle)
//            mBinding.projectViewpager.adapter=adapter
//            mBinding.projectViewpager.offscreenPageLimit=ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
//            mBinding.projectTab.setViewPager2(mBinding.projectViewpager,tabs)
//            mBinding.projectTab.currentTab = 0
//        })
    }

    private fun initArticle(){
        mArticleAdapter = ArticleAdapter(requireActivity(),object : ArticleAdapter.ItemClickListener {
            override fun click() {

                start(requireActivity(), WebActivity().javaClass,false)
            }
        })
        mBinding.videoList.adapter = mArticleAdapter
        mBinding.videoList.layoutManager= GridLayoutManager( requireActivity(),2)
        viewModel.getProjectDetail(1).observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                mArticleAdapter.submitData(it)
            }

        })
    }
}