package com.msh.kotlincoroutines

import com.blankj.utilcode.util.TimeUtils
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author : 马世豪
 * time : 2021/6/24 17
 * email : ma_shihao@yeah.net
 * des :
 */
class combine {

    @Test
    fun test() {
        var dtf1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var dt = dtf1.parse("2021-04-19T05:27:17.9998 08:00")



        println(dt.toString())
        print(TimeUtils.date2String(dt))
    }


}