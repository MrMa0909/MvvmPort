package com.cfox.appdemo.ui.main

import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.MainModel
import com.cfox.appdemo.ui.login.LoginActivity
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.base.uievent.ActivityEvent
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class MainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<MainModel>(viewModelRequest) {

    val loginBtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val activityEventData = ActivityEvent(LoginActivity::class.java)
            runActivityEvent(activityEventData)
        }

    })

}