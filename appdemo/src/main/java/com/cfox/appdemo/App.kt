package com.cfox.appdemo

import android.app.Application
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.app.MpConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val mpConfig = MpConfig.Builder().build()

        MPort.init(this).setConfig(mpConfig)

    }

}