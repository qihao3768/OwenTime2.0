package com.example.owentime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.owentime.R
import com.example.owentime.bean.NoticeBean
import com.example.owentime.databinding.LayoutNoticeBinding
import com.youth.banner.adapter.BannerAdapter


class NoticeAdapter(mDatas: List<NoticeBean>) :
    BannerAdapter<NoticeBean, NoticeAdapter.TopLineHolder>(mDatas) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): TopLineHolder {
//        val view=BannerUtils.getView(parent,R.layout.layout_notice)
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_notice,parent,false)
        view.layoutParams =
            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        val binding=LayoutNoticeBinding.bind(view)
        return TopLineHolder(binding)
    }

    override fun onBindView(holder: TopLineHolder, data: NoticeBean, position: Int, size: Int) {
        holder.bind(data)
    }

    inner class TopLineHolder(var binding:LayoutNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NoticeBean){
            binding.noticeContent.text=data.content
        }
    }
}