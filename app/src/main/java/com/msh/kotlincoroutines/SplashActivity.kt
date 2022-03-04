package com.msh.kotlincoroutines

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.msh.kotlincoroutines.base.BaseActivity
import com.msh.kotlincoroutines.databinding.ActivityMainBinding
import com.msh.kotlincoroutines.databinding.SplashMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author : 马世豪
 * time : 2021/12/16 16
 * email : ma_shihao@yeah.net
 * des :
 */
class SplashActivity : BaseActivity() {

    lateinit var dataBinding: SplashMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.splash_main)
        dataBinding.lifecycleOwner = this


        var index = 5
        lifecycleScope.launch {

            while (true) {

                delay(1000)
                --index
                if (index == 0) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    supportFinishAfterTransition()
                }
                MainScope().launch {
                    dataBinding.text.text = "北京欢迎你${index}"
                }
            }
        }

    }
}