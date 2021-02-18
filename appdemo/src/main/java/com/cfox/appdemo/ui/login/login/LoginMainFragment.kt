package com.cfox.appdemo.ui.login.login

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
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

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.showPwdAction.observe(this,
            Observer { t ->
                val type = t?.let {
                    if (it) {
                        HideReturnsTransformationMethod.getInstance()
                    } else {
                        PasswordTransformationMethod.getInstance()
                    }
                } ?: PasswordTransformationMethod.getInstance()
                binding.etPassword.transformationMethod = type })
    }
}