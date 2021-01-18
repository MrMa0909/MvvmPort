package com.cfox.appdemo

import android.app.Application
import androidx.annotation.NonNull
import com.cfox.appdemo.base.BaseModel
import com.cfox.appdemo.base.BaseViewModel

class MainViewModel(@NonNull application: Application) : BaseViewModel<BaseModel>(application) {

}