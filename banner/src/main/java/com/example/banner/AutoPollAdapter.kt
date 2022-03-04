package com.example.banner

import android.content.Context

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup


/**
 * @author : 马世豪
 * time : 2021/8/24 15
 * email : ma_shihao@yeah.net
 * des :
 */
class AutoPollAdapter(context: Context, list: List<AutoScrollLuckyListReq.DatasBean>) :
    RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder>() {
    private var mContext: Context? = null
    private val mData: List<AutoScrollLuckyListReq.DatasBean>


    init {
        mContext = context
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {

        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.auto_list_item, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val datasBean: AutoScrollLuckyListReq.DatasBean = mData[position % mData.size]
        holder.content.setText(datasBean.phone.toString() + " 获得 " + datasBean.giftName)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView

        init {
            content = itemView.findViewById(R.id.content)
        }
    }

    init {
        mData = list
    }
}
