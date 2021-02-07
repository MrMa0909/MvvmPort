package com.cfox.appdemo

import android.app.Application
import com.cfox.appdemo.strategy.*
import com.cfox.appdemo.utils.ToastUtils
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.app.MpApplication
import com.cfox.mvvmprot.app.MpConfig
import com.cfox.mvvmprot.datapersistence.DataPersist

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)

        val mpConfigBuilder = MpConfig.Builder()
//        mpConfigBuilder.activityStrategy = AppActivityStrategy()
//        mpConfigBuilder.fragmentStrategy = AppFragmentStrategy()
//        mpConfigBuilder.dialogStrategy = AppDialogStrategy()
//        mpConfigBuilder.otherStrategy = AppOtherStrategy()
//        mpConfigBuilder.dataPersistStrategy = AppDataPersistStrategy()

        MPort.init(this).setConfig(mpConfigBuilder.build())

    }

}