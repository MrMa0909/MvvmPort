package com.cfox.appdemo.ui.main

import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.MainModel
import com.cfox.appdemo.ui.login.LoginActivity
import com.cfox.mvvmprot.base.strategy.impl.event.ActivityEvent
import com.cfox.mvvmprot.base.strategy.impl.event.OrigFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class MainViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<MainModel>(viewModelRequest) {

    val loginBtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val activityEventData = ActivityEvent(LoginActivity::class.java)
            runActivityEvent(activityEventData)
        }

    })

    // 传统方式启动fragment
    val show1BtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val requestType = OrigFragmentEvent.OrigEventType.SHOW( R.id.head_fragment1, MainFragment1::class.java)
            val appOrigFragmentEvent = OrigFragmentEvent(requestType)
            runFragmentEvent(appOrigFragmentEvent)
        }

    })

    val show2BtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val requestType = OrigFragmentEvent.OrigEventType.SHOW(R.id.head_fragment2, MainFragment2::class.java)
            val appOrigFragmentEvent = OrigFragmentEvent(requestType)
            runFragmentEvent(appOrigFragmentEvent)
        }

    })

}