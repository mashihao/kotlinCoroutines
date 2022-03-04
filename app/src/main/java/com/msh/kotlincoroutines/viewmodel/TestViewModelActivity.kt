package com.msh.kotlincoroutines.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.msh.kotlincoroutines.R
import com.msh.kotlincoroutines.base.BaseActivity

class TestViewModelActivity : BaseActivity() {


    val viewModel by viewModels<UserModel>()
    lateinit var viewModel2: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view_model)
        viewModel2 = ViewModelProviders.of(this).get(UserModel::class.java)
        viewModel2 = ViewModelProvider(this).get(UserModel::class.java)
//

        toast("${this is HasDefaultViewModelProviderFactory}")
    }


     class UserModel(application: Application) : AndroidViewModel(application) {


    }
}