package com.cfox.appdemo.ui.login.login

import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.ui.login.LoginViewModel
import com.cfox.mvvmprot.base.strategy.impl.event.NavFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class LoginMainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<LoginMainModel>(viewModelRequest){

    val btnRegister = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val navFragmentEvent = NavFragmentEvent(R.id.nav_login_host_fragment, R.id.action_loginMainFragment_to_registerUserFragment)
            runFragmentEvent(navFragmentEvent)
        }
    })

    val btnForget = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val navFragmentEvent = NavFragmentEvent(R.id.nav_login_host_fragment, R.id.action_loginMainFragment_to_forgetPasswordFragment)
            runFragmentEvent(navFragmentEvent)
        }
    })

}