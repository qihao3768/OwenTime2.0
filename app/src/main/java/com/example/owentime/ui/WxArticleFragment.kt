package com.example.owentime.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.adapter.WxArticleAdapter
import com.example.owentime.base.BaseFragment
import com.example.owentime.databinding.WxArticleFragmentBinding
import com.example.owentime.start
import com.example.owentime.vm.PublicViewModel
import com.example.owentime.web.WebActivity
import kotlinx.coroutines.launch
private const val ARG_PARAM1 = "cid"
class WxArticleFragment() : BaseFragment(R.layout.wx_article_fragment) {

    private var param1: Int=-1
    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            WxArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    private  val mViewModel by viewModels<PublicViewModel>()
    private val mBinding by viewBinding(WxArticleFragmentBinding::bind)
    private val mAdapter=WxArticleAdapter(object : WxArticleAdapter.ItemClickListener {
        override fun click(url: String) {
            start(requireActivity(),WebActivity().javaClass,"url",url)
        }
    })
    override fun initData() {
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        mViewModel.getWxArticleDetail(cid = param1).observe(this, Observer {
            mBinding.wxarticleList.adapter=mAdapter
            lifecycleScope.launch {
                mAdapter.submitData(it)
            }
        })
    }
}