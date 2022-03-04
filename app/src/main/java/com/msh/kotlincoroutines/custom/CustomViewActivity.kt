package com.msh.kotlincoroutines.custom

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import com.msh.kotlincoroutines.R
import com.msh.kotlincoroutines.base.BaseActivity
import com.msh.kotlincoroutines.databinding.ActivityMainBinding
import com.msh.kotlincoroutines.databinding.CustomViewActivityBinding

/**
 * @author : 马世豪
 * time : 2022/3/4 15
 * email : ma_shihao@yeah.net
 * des :
 */
class CustomViewActivity : BaseActivity() {


    lateinit var dataBinding: CustomViewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.custom_view_activity)
        dataBinding.lifecycleOwner = this
    }
}