package com.cfox.app_wrapper

import android.app.Application
import com.cfox.mvvmprot.base.MpViewModel

class MainViewModelMp(application: Application) : MpViewModel<MainMode>(application) {


    override fun onCreate() {
        super.onCreate()

    }
}