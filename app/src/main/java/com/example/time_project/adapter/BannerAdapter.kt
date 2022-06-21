package com.example.time_project.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.time_project.R
import com.youth.banner.config.BannerConfig
import com.youth.banner.holder.IViewHolder
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.util.BannerUtils

abstract class BannerAdapter<T, VH : RecyclerView.ViewHolder?>(datas: List<T>?) :
    RecyclerView.Adapter<VH>(), IViewHolder<T, VH> {
    protected var mDatas: List<T>? = ArrayList()
    private var mOnBannerListener: OnBannerListener<T>? = null
    var viewHolder: VH? = null
        private set
    private var mIncreaseCount = BannerConfig.INCREASE_COUNT

    /**
     * 设置实体集合（可以在自己的adapter自定义，不一定非要使用）
     *
     * @param datas
     */
    fun setDatas(datas: List<T>?) {
        var datas = datas
        if (datas == null) {
            datas = ArrayList()
        }
        mDatas = datas
        notifyDataSetChanged()
    }

    /**
     * 获取指定的实体（可以在自己的adapter自定义，不一定非要使用）
     *
     * @param position 真实的position
     * @return
     */
    fun getData(position: Int): T {
        return mDatas!![position]
    }

    /**
     * 获取指定的实体（可以在自己的adapter自定义，不一定非要使用）
     *
     * @param position 这里传的position不是真实的，获取时转换了一次
     * @return
     */
    fun getRealData(position: Int): T {
        return mDatas!![getRealPosition(position)]
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        viewHolder = holder
        val real = getRealPosition(position)
        val data = mDatas!![real]
        holder!!.itemView.setTag(R.id.banner_data_key, data)
        holder.itemView.setTag(R.id.banner_pos_key, real)
        onBindView(holder, mDatas!![real], real, realCount)
        if (mOnBannerListener != null) {
            holder.itemView.setOnClickListener { view: View? ->
                mOnBannerListener!!.OnBannerClick(
                    data,
                    real
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = onCreateHolder(parent, viewType)
        vh!!.itemView.setOnClickListener { v: View? ->
            if (mOnBannerListener != null) {
                val data = vh.itemView.getTag(R.id.banner_data_key) as T
                val real = vh.itemView.getTag(R.id.banner_pos_key) as Int
                mOnBannerListener!!.OnBannerClick(data, real)
            }
        }
        return vh
    }

    override fun getItemCount(): Int {
        return if (realCount > 1) realCount + mIncreaseCount else realCount
    }

    val realCount: Int
        get() = if (mDatas == null) 0 else mDatas!!.size

    fun getRealPosition(position: Int): Int {
        return BannerUtils.getRealPosition(
            mIncreaseCount == BannerConfig.INCREASE_COUNT, position,
            realCount
        )
    }

    fun setOnBannerListener(listener: OnBannerListener<T>?) {
        mOnBannerListener = listener
    }

    fun setIncreaseCount(increaseCount: Int) {
        mIncreaseCount = increaseCount
    }

    init {
        setDatas(datas)
    }
}