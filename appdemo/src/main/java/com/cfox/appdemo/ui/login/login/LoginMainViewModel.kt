package com.cfox.appdemo.ui.login.login

import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.bean.LoginBean
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.utils.ToastUtils
import com.cfox.mvvmprot.support.strategy.event.NavFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam
import com.cfox.mvvmprot.support.binding.command.BindingAction
import com.cfox.mvvmprot.support.binding.command.BindingCommand
import com.cfox.mvvmprot.utils.SingleLiveEvent

class LoginMainViewModel(viewModelParam: ViewModelParam) : BaseViewModel<LoginMainModel>(viewModelParam){

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
            val navFragmentEvent =
                NavFragmentEvent(
                    R.id.nav_login_host_fragment,
                    eventType
                )
            sendFragmentEvent(navFragmentEvent)
        }
    })

    val btnForget = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val eventType = NavFragmentEvent.NavEventType.NAVIGATE(R.id.action_loginMainFragment_to_forgetPasswordFragment)
            val navFragmentEvent =
                NavFragmentEvent(
                    R.id.nav_login_host_fragment,
                    eventType
                )
            sendFragmentEvent(navFragmentEvent)
        }
    })

    val showPwdAction = SingleLiveEvent(false)

    val btnShowPwd = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val value = showPwdAction.value?.let {
                !it
            }
            showPwdAction.value = value
        }
    })
}