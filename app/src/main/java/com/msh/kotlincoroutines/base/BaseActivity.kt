package com.msh.kotlincoroutines.base

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * @author : 马世豪
 * time : 2021/11/10 14
 * email : ma_shihao@yeah.net
 * des :
 */
open class BaseActivity : AppCompatActivity() {

    fun log(any:Any) {
        LogUtils.eTag("MSH----->","$any")
    }

    fun toast(any:Any){
        ToastUtils.showShort("$any")
    }
}