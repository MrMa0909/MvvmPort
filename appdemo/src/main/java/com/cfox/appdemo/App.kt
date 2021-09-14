package com.cfox.appdemo

import android.app.Application
import com.cfox.appdemo.utils.ToastUtils
import com.cfox.mvvmprot.support.MPortSupport

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)

//        val mpConfigBuilder = MpConfig.Builder()
//        mpConfigBuilder.activityStrategy = AppActivityStrategy()
//        mpConfigBuilder.fragmentStrategy = AppFragmentStrategy()
//        mpConfigBuilder.dialogStrategy = AppDialogStrategy()
//        mpConfigBuilder.otherStrategy = AppOtherStrategy()
//        mpConfigBuilder.dataPersistStrategy = AppDataPersistStrategy()

//        MPort.init(this).setConfig(mpConfigBuilder.build())

        MPortSupport.init(this)

    }

}