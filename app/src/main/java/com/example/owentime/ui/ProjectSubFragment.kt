package com.example.owentime.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.ProjectListAdapter
import com.example.owentime.base.BaseFragment
import com.example.owentime.databinding.ProjectSubFragmentBinding
import com.example.owentime.start
import com.example.owentime.vm.ProjectViewModel
import com.example.owentime.web.WebActivity
import kotlinx.coroutines.launch
private const val ARG_PARAM1 = "cid"
class ProjectSubFragment : BaseFragment(R.layout.project_sub_fragment) {

    private var param1: Int=-1
    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ProjectSubFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    private val mViewModel by viewModels<ProjectViewModel>()
    private val mBinding by viewBinding(ProjectSubFragmentBinding::bind)
    private val mAdapter= ProjectListAdapter(object : ProjectListAdapter.ItemClickListener {
        override fun click(url: String) {
            start(requireActivity(),WebActivity().javaClass,"url",url)
        }
    })


    override fun initData() {
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        mBinding.projectList.adapter=mAdapter
        mViewModel.getProjectDetail(param1).observe(this, Observer {
            lifecycleScope.launch {
//                mAdapter.submitData(it)
            }

        })
    }
}