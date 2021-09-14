package com.cfox.app_wrapper

import android.app.Application
import com.cfox.mvvmprot.base.viewmodel.MPViewModel

class MainViewModelMP(application: Application) : MPViewModel<MainMode>(application) {


    override fun onCreate() {
        super.onCreate()

    }
}