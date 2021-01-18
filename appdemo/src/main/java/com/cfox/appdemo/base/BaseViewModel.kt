package com.cfox.appdemo.base

import android.app.Application
import androidx.annotation.NonNull
import com.cfox.mvvmprot.base.MpModel
import com.cfox.mvvmprot.base.MpViewModel

open class BaseViewModel<M : MpModel>(@NonNull application: Application) : MpViewModel<M>(application) {

}