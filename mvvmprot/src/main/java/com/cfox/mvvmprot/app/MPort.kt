package com.cfox.mvvmprot.app

import android.app.Application

object MPort {

    private var mpConfigWrapper = MPConfigWrapper()
    fun init(application: Application) : MPConfigWrapper {
        AppContext.init(application.applicationContext)
        mpConfigWrapper.setConfig(MPConfig.Builder().build())
        return mpConfigWrapper
    }

    internal fun getConfig() : MPConfig {
        return mpConfigWrapper.getConfig()
    }
}