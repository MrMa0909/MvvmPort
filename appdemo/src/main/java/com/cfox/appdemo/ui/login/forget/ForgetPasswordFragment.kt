package com.cfox.appdemo.ui.login.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaserFragment
import com.cfox.appdemo.databinding.FragmentLoginForgetPasswordBinding

class ForgetPasswordFragment : BaserFragment<FragmentLoginForgetPasswordBinding, ForgetPasswordViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_login_forget_password
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }


}