package com.cfox.mvvmprot.support

import android.app.Application
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.app.MPConfig
import com.cfox.mvvmprot.support.datapersistence.DefaultDataPersistStrategy
import com.cfox.mvvmprot.support.strategy.DefActivityStrategy
import com.cfox.mvvmprot.support.strategy.DefFragmentStrategy

object MPortSupport {

    fun init(application: Application) {
        val mpConfigBuilder = MPConfig.Builder()
        mpConfigBuilder.activityStrategy = DefActivityStrategy()
        mpConfigBuilder.fragmentStrategy = DefFragmentStrategy()
        mpConfigBuilder.addStrategy(DefaultDataPersistStrategy())
//        mpConfigBuilder.dialogStrategy = AppDialogStrategy()
//        mpConfigBuilder.otherStrategy = AppOtherStrategy()

        MPort.init(application).setConfig(mpConfigBuilder.build())

    }

    fun init(application: Application, config: MPConfig) {
        MPort.init(application).setConfig(config)
    }
}