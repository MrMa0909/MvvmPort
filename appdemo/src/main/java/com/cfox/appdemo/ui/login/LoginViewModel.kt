package com.cfox.appdemo.ui.login

import androidx.annotation.NonNull
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.login.LoginModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam

class LoginViewModel(@NonNull viewModelParam: ViewModelParam) : BaseViewModel<LoginModel>(viewModelParam) {

    fun getModelName() : String {
        return model.name
    }
}