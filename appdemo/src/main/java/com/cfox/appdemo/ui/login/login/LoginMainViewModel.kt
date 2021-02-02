package com.cfox.appdemo.ui.login.login

import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.ui.login.LoginViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class LoginMainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<LoginMainModel>(viewModelRequest){

    private val shareViewModel = getShareViewModel<LoginViewModel>()

    val btnRegister = BindingCommand<Unit>(object : BindingAction {
        override fun call() {


        }
    })

}