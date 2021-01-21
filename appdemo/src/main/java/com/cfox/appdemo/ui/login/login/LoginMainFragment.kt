package com.cfox.appdemo.ui.login.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaserFragment
import com.cfox.appdemo.databinding.FragmentLoginMainBinding

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
}