package com.cfox.mvvmprot.app

class MpConfigWrapper {
    private lateinit var mpConfig : MpConfig

    fun setConfig(config: MpConfig) {
        config.init(AppContext.getContext())
        mpConfig = config
    }

    internal fun getConfig() : MpConfig {
        return mpConfig
    }
}