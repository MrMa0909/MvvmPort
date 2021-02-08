package com.cfox.appdemo.ui.login.login

import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.bean.LoginBean
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.ui.login.LoginViewModel
import com.cfox.appdemo.utils.ToastUtils
import com.cfox.mvvmprot.base.strategy.impl.event.NavFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class LoginMainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<LoginMainModel>(viewModelRequest){

    val loginInfo = LoginBean()

    val btnLogin = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            if (checkLoginInfo()) {
                requestLogin()
            } else {
                ToastUtils.show("用户名和密码不可为空")
            }
        }
    })

    private fun checkLoginInfo() : Boolean {
        return !(loginInfo.userName.get().isNullOrEmpty() || loginInfo.password.get().isNullOrEmpty())
    }

    private fun requestLogin() {
        val subscribe = model?.loginUser(loginInfo)?.subscribe {
            if (it == 0) {
                ToastUtils.show("登录成功")
                finish()
            } else {
                ToastUtils.show("登录失败")
            }
        }

        addSubscribe(subscribe)
    }

    val btnRegister = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val eventType = NavFragmentEvent.NavEventType.NAVIGATE(R.id.action_loginMainFragment_to_registerUserFragment)
            val navFragmentEvent = NavFragmentEvent(R.id.nav_login_host_fragment, eventType)
            runFragmentEvent(navFragmentEvent)
        }
    })

    val btnForget = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val eventType = NavFragmentEvent.NavEventType.NAVIGATE(R.id.action_loginMainFragment_to_forgetPasswordFragment)
            val navFragmentEvent = NavFragmentEvent(R.id.nav_login_host_fragment, eventType)
            runFragmentEvent(navFragmentEvent)
        }
    })
}