package com.cfox.appdemo.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cfox.mvvmprot.base.viewmodel.MPSViewModel

class MainShareViewModel(application: Application) : MPSViewModel(application) {

    val shareValue = "Main Share view Model"

    val shareValueLiveData = MutableLiveData("default")

}