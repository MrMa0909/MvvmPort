package com.cfox.appdemo.data.login

import android.util.Log
import com.cfox.appdemo.base.BaseModel
import com.cfox.appdemo.data.bean.LoginBean
import com.cfox.mvvmprot.datapersistence.DataPersist
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginMainModel : BaseModel() {
    companion object {
        private const val TAG = "LoginMainModel"
    }

    fun loginUser(loginBean: LoginBean) : Observable<Int> {

        return Observable.create<Int> {
            var isSuccess = false

            loginBean.userName.get()?.let {
                val pwd = DataPersist.getString(it)
                isSuccess = pwd == loginBean.password.get()
            }
            val resultCode = if (isSuccess) 0 else 1
            Log.d(
                TAG,
                "loginUser: resultCode : $resultCode   name: ${loginBean.userName.get()}   pwd : ${loginBean.password.get()}"
            )
            it.onNext(resultCode)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}