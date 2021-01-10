package com.cfox.app_wrapper

import android.app.Application
import com.cfox.mvvmprot.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<MainMode>(application) {


    override fun onCreate() {
        super.onCreate()

    }
}