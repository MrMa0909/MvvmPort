package com.cfox.appdemo.ui.login.register

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.cfox.appdemo.R
import com.cfox.appdemo.base.BaseViewModel
import com.cfox.appdemo.data.bean.RegisterBean
import com.cfox.appdemo.data.login.RegisterUserModel
import com.cfox.appdemo.utils.ToastUtils
import com.cfox.mvvmprot.base.strategy.impl.event.NavFragmentEvent
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.binding.command.BindingAction
import com.cfox.mvvmprot.binding.command.BindingCommand

class RegisterUserViewModel(viewModelRequest: ViewModelRequest) : BaseViewModel<RegisterUserModel>(viewModelRequest) {
    companion object {
        private const val TAG = "RegisterUserViewModel"
    }
    val registerInfo = RegisterBean()
    var btnText = getApplication<Application>().getString(R.string.register)

    val btnRegister = BindingCommand<Unit>(object : BindingAction {
        override fun call() {
            if (checkUserInfo()) {
                requestRegister()
            } else {
                ToastUtils.show("用户名和密码不能为空")
            }
        }
    })

    private fun checkUserInfo() : Boolean {
        return !(registerInfo.userName.get().isNullOrEmpty() || registerInfo.userPwd.get().isNullOrEmpty())
    }

    private fun requestRegister() {
        btnText = getApplication<Application>().getString(R.string.registering)
        val disposable = model?.registerUser(registerInfo)?.subscribe {
            Log.d(TAG, "call: cack status : $it")
            if (it == 0) {
                ToastUtils.show("注册成功")
                val navFragmentEvent = NavFragmentEvent(
                    R.id.nav_login_host_fragment,
                    NavFragmentEvent.NavEventType.NAVIGATE_UP
                )
                runFragmentEvent(navFragmentEvent)
            } else {
                ToastUtils.show("注册失败")
            }
        }

        addSubscribe(disposable)
        Log.d(TAG, "call: user name: ${registerInfo.userName.get()}   pwd : ${registerInfo.userPwd.get()}")
    }
}