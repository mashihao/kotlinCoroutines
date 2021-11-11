package com.msh.kotlincoroutines

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author : 马世豪
 * time : 2021/11/10 14
 * email : ma_shihao@yeah.net
 * des :
 */
fun <T : ViewDataBinding> BaseViewHolder.bind() = DataBindingUtil.bind<T>(itemView)