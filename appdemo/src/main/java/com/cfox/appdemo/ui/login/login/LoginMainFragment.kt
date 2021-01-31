package com.cfox.appdemo.ui.login.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaserFragment
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.data.login.LoginModel
import com.cfox.appdemo.databinding.FragmentLoginMainBinding
import com.cfox.appdemo.ui.login.LoginViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest

class LoginMainFragment : BaserFragment<FragmentLoginMainBinding, LoginMainViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_login_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
//        val sharVM = viewModel.getShareViewModel<LoginViewModel>()

    }
}