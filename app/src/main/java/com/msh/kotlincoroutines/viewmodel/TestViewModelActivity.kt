package com.msh.kotlincoroutines.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msh.kotlincoroutines.R
import com.msh.kotlincoroutines.base.BaseActivity

class TestViewModelActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view_model)
    }
}