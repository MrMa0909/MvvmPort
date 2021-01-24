package com.cfox.appdemo.ui.login

import android.os.Bundle
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseActivity
import com.cfox.appdemo.data.login.LoginModel
import com.cfox.appdemo.databinding.ActivityLoginBinding
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel> () {

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}