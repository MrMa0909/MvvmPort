package com.cfox.mvvmprot.app

import android.app.Application

object MPort {

    private var mpConfigWrapper = MpConfigWrapper()
    fun init(application: Application) : MpConfigWrapper {
        AppContext.init(application.applicationContext)
        mpConfigWrapper.setConfig(MpConfig.Builder().build())
        return mpConfigWrapper
    }

    internal fun getConfig() : MpConfig {
        return mpConfigWrapper.getConfig()
    }
}