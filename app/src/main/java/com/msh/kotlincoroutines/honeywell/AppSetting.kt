package com.msh.kotlincoroutines.honeywell

import com.blankj.utilcode.util.SPUtils

/**
 * @author : 马世豪
 * time : 2022/4/20 14
 * email : ma_shihao@yeah.net
 * des :
 */
object AppSetting {


    @JvmStatic
    fun getPrint_ip(): String {
        return SPUtils.getInstance().getString("printIP", "")
    }

    @JvmStatic
    fun savePrint_ip(ip: String) {
        SPUtils.getInstance().put("printIP", ip)
    }

    @JvmStatic
    fun getPrint_port(): Int {
        return SPUtils.getInstance().getInt("port", 0)
    }

    @JvmStatic
    fun savePrint_port(port: Int) {
        SPUtils.getInstance().put("port", port)
    }

}