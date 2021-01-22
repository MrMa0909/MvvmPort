package com.cfox.appdemo.ui.login.login

import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.login.LoginMainModel
import com.cfox.appdemo.ui.login.LoginViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest

class LoginMainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<LoginMainModel>(viewModelRequest){

    private val shareViewModel = getShareViewModel<LoginViewModel>()

    fun getShareName() : String {
        return shareViewModel.getModelName()
    }

}