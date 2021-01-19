package com.cfox.appdemo.ui.main

import android.app.Application
import android.content.Intent
import androidx.annotation.NonNull
import com.cfox.appdemo.base.BaseModel
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.ui.login.LoginActivity
import com.cfox.mvvmprot.base.eventdata.ActivityEventData
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand
import com.cfox.mvvmprot.binding.command.BindingConsumer
import kotlin.reflect.KProperty1

class MainViewModel(@NonNull application: Application) : BaseViewModel<BaseModel>(application) {

    val loginBtnClick = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            val activityEventData = ActivityEventData(LoginActivity::class.java)
            runActivityEvent(activityEventData)
        }

    })

}