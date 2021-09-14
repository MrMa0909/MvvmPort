package com.cfox.mvvmprot.base.viewmodel

import android.app.Application
import com.cfox.mvvmprot.base.model.MpModel

class ViewModelParam {
    internal lateinit var application: Application
    internal lateinit var shareViewMode : MPSViewModel

    private var model : MpModel? = null
    internal fun getModel() : MpModel? {
        return model
    }

    fun setModel(model: MpModel) : ViewModelParam {
        this.model = model
        return this
    }
}