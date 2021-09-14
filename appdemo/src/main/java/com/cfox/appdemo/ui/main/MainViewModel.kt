package com.cfox.appdemo.ui.main

import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.MainModel
import com.cfox.appdemo.ui.login.LoginActivity
import com.cfox.mvvmprot.support.strategy.event.ActivityEvent
import com.cfox.mvvmprot.support.strategy.event.OrigFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam
import com.cfox.mvvmprot.support.binding.command.BindingAction
import com.cfox.mvvmprot.support.binding.command.BindingCommand

class MainViewModel(viewModelParam: ViewModelParam) : BaseViewModel<MainModel>(viewModelParam) {

    // 启动 activity
    val loginBtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val activityEventData = ActivityEvent(LoginActivity::class.java)
            sendActivityEvent(activityEventData)
        }
    })

    // 传统方式启动fragment
    val btnShowClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val requestType = OrigFragmentEvent.OrigEventType.SHOW( R.id.head_fragment1, MainFragment1::class.java)
            val appOrigFragmentEvent = OrigFragmentEvent(requestType)
            sendFragmentEvent(appOrigFragmentEvent)
        }
    })

    val btnShowUp = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val requestType = OrigFragmentEvent.OrigEventType.SHOW(R.id.head_fragment2, MainShareFragment::class.java)
            val appOrigFragmentEvent = OrigFragmentEvent(requestType)
            sendFragmentEvent(appOrigFragmentEvent)
        }
    })

    val btnSetDataClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            getShareViewModel<MainShareViewModel>().shareValueLiveData.value = "new Data  ${System.currentTimeMillis()}"
        }
    })
}