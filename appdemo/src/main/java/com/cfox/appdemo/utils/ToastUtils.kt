package com.cfox.appdemo.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    var toast : Toast ? = null
    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }


    fun show(msg: String) {
        toast?.cancel()
        toast =  Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }
}