package com.cfox.appdemo.base

import androidx.annotation.NonNull
import com.cfox.mvvmprot.base.model.MpModel
import com.cfox.mvvmprot.base.viewmodel.MpViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest

open class BaseViewModel<M : MpModel>(@NonNull viewModelRequest: ViewModelRequest) : MpViewModel<M>(viewModelRequest) {

}