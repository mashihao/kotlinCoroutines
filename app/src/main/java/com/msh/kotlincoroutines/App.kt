package com.msh.kotlincoroutines

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Handler
import android.os.Message

/**
 * @author : 马世豪
 * time : 2021/7/8 13
 * email : ma_shihao@yeah.net
 * des :
 */
class App : Application() {


    companion object {
        lateinit var instance: Context
    }


    override fun onCreate() {
        super.onCreate()
        instance = this.applicationContext
        //TODO 加载  android 彩蛋
        // 需要去Androidmanifest  删除 theme
//        hookActivity()
    }

    private fun hookActivity() {

        try {
            val atclazz = Class.forName("android.app.ActivityThread")
            val atField = atclazz.getDeclaredField("sCurrentActivityThread")
            atField.isAccessible = true
            val activityThread = atField.get(null)
            val mHField = atclazz.getDeclaredField("mH")
            mHField.isAccessible = true
            val mH = mHField.get(activityThread)

            val mcallBackField = Handler::class.java.getDeclaredField("mCallback")
            mcallBackField.isAccessible = true
            val mHcallback = HandlerCallback()
            mcallBackField.set(mH, mHcallback)

        } catch (ex: Exception) {

        }
    }

    private class HandlerCallback : android.os.Handler.Callback {
        @SuppressLint("PrivateApi")
        override fun handleMessage(msg: Message): Boolean {
            val result = false
            if (msg.what == 159) {
                try {
                    val ctClazz = Class.forName("android.app.servertransaction.ClientTransaction")
                    val launchActivityItemClazz =
                        Class.forName("android.app.servertransaction.LaunchActivityItem")
                    val mActivityCallbackField = ctClazz.getDeclaredField("mActivityCallbacks")
                    mActivityCallbackField.isAccessible = true
                    val mActivityCallbackobj = mActivityCallbackField.get(msg.obj)
                    val list = mActivityCallbackobj as List<*>
                    list.forEach {
                        if (launchActivityItemClazz.isInstance(it)) {
                            val mIntentField = launchActivityItemClazz.getDeclaredField("mIntent")
                            mIntentField.isAccessible = true
                            val intent = Intent().setClassName(
                                "android",
                                "com.android.internal.app.PlatLogoActivity"
                            )
                            mIntentField.set(it, intent)
                            return@forEach
                        }
                    }
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                    return false
                }
            }
            return result
        }
    }


}