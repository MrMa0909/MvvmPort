package com.cfox.mvvmprot.app

class MPConfigWrapper {
    private lateinit var mpConfig : MPConfig

    fun setConfig(config: MPConfig) {
        config.init(AppContext.getContext())
        mpConfig = config
    }

    internal fun getConfig() : MPConfig {
        return mpConfig
    }
}