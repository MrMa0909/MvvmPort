package com.cfox.mvvmprot.base

import android.app.Application
import com.cfox.mvvmprot.app.AppContext

open class MpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)

    }
}