package com.example.banner

/**
 * @author : 马世豪
 * time : 2021/8/24 15
 * email : ma_shihao@yeah.net
 * des :
 */

data class AutoScrollLuckyListReq(
    var datas: MutableList<DatasBean>,
    var msg: String,
    var ret: Int
){
    data class DatasBean(
        var giftName: String,
        var phone: String
    )
}

