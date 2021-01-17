package com.cfox.mvvmprot.app

import android.content.Context

object MPort {

    private var mpConfig : MpConfig ? = null

    fun init(context: Context) : MPort {
        AppContext.init(context)
        return this
    }

    fun setConfig(config: MpConfig) {
        mpConfig = config
    }

    internal fun getConfig() : MpConfig? {
        return mpConfig
    }
}