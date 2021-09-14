package com.cfox.appdemo.base

import androidx.annotation.NonNull
import com.cfox.mvvmprot.base.model.MpModel
import com.cfox.mvvmprot.base.viewmodel.MPViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam

open class BaseViewModel<M : MpModel>(@NonNull viewModelParam: ViewModelParam) : MPViewModel<M>(viewModelParam) {

}