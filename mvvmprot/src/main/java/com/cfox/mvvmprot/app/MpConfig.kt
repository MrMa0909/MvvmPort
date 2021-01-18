package com.cfox.mvvmprot.app

import com.cfox.mvvmprot.base.eventstrategy.*
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultActivityStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultDialogStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultFragmentStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultOtherStrategy

class MpConfig {
    private val strategyManager = MpStrategyManager()
    private constructor() {}

    private constructor(builder: Builder) {
        strategyManager.setStrategy(StrategyType.ACTIVITY, builder.activityStrategy ?: DefaultActivityStrategy())
        strategyManager.setStrategy(StrategyType.FRAGMENT, builder.fragmentStrategy ?: DefaultFragmentStrategy())
        strategyManager.setStrategy(StrategyType.DIALOG, builder.dialogStrategy ?: DefaultDialogStrategy())
        strategyManager.setStrategy(StrategyType.OTHER, builder.otherStrategy ?: DefaultOtherStrategy())
    }

    fun getStrategyManager() : MpStrategyManager {
        return strategyManager
    }

    class Builder {
        var activityStrategy: IActivityStrategy ? = null
        var fragmentStrategy: IFragmentStrategy ? = null
        var dialogStrategy: IDialogStrategy ? = null
        var otherStrategy: IOtherStrategy ? = null
        fun build() : MpConfig {
            return MpConfig(this)
        }
    }
}