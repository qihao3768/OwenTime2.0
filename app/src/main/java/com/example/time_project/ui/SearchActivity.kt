package com.example.time_project.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivitySearchBinding
import com.example.time_project.util.FloatLayout

class SearchActivity : BaseActivity(R.layout.activity_search) {
    private val mBinding by viewBinding(ActivitySearchBinding::bind)
    override fun initData() {
        var mData = arrayListOf<String>()
        mData.add("1")
        mData.add("12")
        mData.add("13")
        mData.add("14")
        mData.add("15")
        mData.add("16")

            mBinding.floatLayoutRecommend.setAdapter(object : FloatLayout.FloatAdapter() {
                override fun getCount(): Int {
                    return mData.size
                }

                override fun getLayoutID(): Int {
                    return R.layout.item_search_grid
                }

                override fun onBindView(v: View, position: Int) {
                    if (position<4){
                        v.findViewById<TextView>(R.id.iv_hot_words).text = mData[position]
                        v.findViewById<ImageView>(R.id.iv_hot).visibility = View.VISIBLE
                    }else{
                        v.findViewById<ImageView>(R.id.iv_hot).visibility = View.GONE
                    }
                }

            })


        mBinding.floatLayoutHistory.setAdapter(object : FloatLayout.FloatAdapter() {
            override fun getCount(): Int {
                return mData.size
            }

            override fun getLayoutID(): Int {
                return R.layout.item_search_grid
            }

            override fun onBindView(v: View, position: Int) {
                if (position<4){
                    v.findViewById<TextView>(R.id.iv_hot_words).text = mData[position]+"789789"
                    v.findViewById<ImageView>(R.id.iv_hot).visibility = View.VISIBLE
                }else{
                    v.findViewById<ImageView>(R.id.iv_hot).visibility = View.GONE
                }
            }

        })

        mBinding.historyDelete.setOnClickListener {

        }
        }

}