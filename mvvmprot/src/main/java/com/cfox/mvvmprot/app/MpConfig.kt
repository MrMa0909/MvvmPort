package com.cfox.mvvmprot.app

import com.cfox.mvvmprot.base.eventstrategy.*
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultActivityStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultDialogStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultFragmentStrategy

class MpConfig(val builder: MpConfig.Builder) {

    private val strategyManager = MpStrategyManager()

    init {
        strategyManager.setStrategy(StrategyType.ACTIVITY, builder.activityStrategy ?: DefaultActivityStrategy())
        strategyManager.setStrategy(StrategyType.FRAGMENT, DefaultFragmentStrategy())
        strategyManager.setStrategy(StrategyType.DIALOG, DefaultDialogStrategy())
    }

    fun getStrategyManager() : MpStrategyManager {
        return strategyManager
    }

    class Builder {
        var activityStrategy: IActivityStrategy ? = null
        var fragmentStrategy: IFragmentStrategy ? = null
        var dialogStrategy: IDialogStrategy ? = null
        fun build() : MpConfig {
            return MpConfig(this)
        }
    }
}