package com.cfox.appdemo.data.login

import android.util.Log
import com.cfox.appdemo.base.BaseModel
import com.cfox.appdemo.data.bean.RegisterBean
import com.cfox.mvvmprot.support.datapersistence.getWriter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterUserModel : BaseModel() {

    companion object {
        private const val TAG = "RegisterUserModel"
    }

    fun registerUser(userBean: RegisterBean) : Observable<Int> {
        return Observable.create<Int> {
            val registerStatus = (Math.random() * 10).toInt() % 2
            try {
                //模拟注册
                Thread.sleep(5000)
            } catch (e: Exception) {

            }

            if (registerStatus == 0) {
                userBean.userName.get()?.let {
                    getWriter().putString(it, userBean.userPwd.get() )
                }
            }

            Log.d(TAG, "registerUser: status : $registerStatus")
            it.onNext(registerStatus)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}
