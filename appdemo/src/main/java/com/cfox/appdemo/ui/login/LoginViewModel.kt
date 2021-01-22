package com.cfox.appdemo.ui.login

import androidx.annotation.NonNull
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.login.LoginModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest

class LoginViewModel(@NonNull viewModelRequest: ViewModelRequest) : BaseViewModel<LoginModel>(viewModelRequest) {

    fun getModelName() : String {
        return model?.name ?: ""
    }
}