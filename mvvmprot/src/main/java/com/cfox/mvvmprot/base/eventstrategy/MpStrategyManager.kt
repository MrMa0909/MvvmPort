package com.cfox.mvvmprot.base.eventstrategy

import android.util.Log
import com.cfox.mvvmprot.BuildConfig
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultActivityStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultDialogStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultFragmentStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultOtherStrategy

class MpStrategyManager {

    private val strategyMap = mutableMapOf<Int, IStrategy<*>>()

    init {
        strategyMap[StrategyType.ACTIVITY.getType()] = DefaultActivityStrategy()
        strategyMap[StrategyType.FRAGMENT.getType()] = DefaultFragmentStrategy()
        strategyMap[StrategyType.DIALOG.getType()] = DefaultDialogStrategy()
        strategyMap[StrategyType.OTHER.getType()] = DefaultOtherStrategy()
    }

    fun setStrategy(strategyType: StrategyType, strategy : IStrategy<*>) {
        if (BuildConfig.DEBUG) {
            strategyMap.forEach {
                if (it.key == strategyType.getType()) {
                    Log.e("TAG", "This StrategyType already exists ， key : ${strategyType.getType()}")
                }
            }
        }
        strategyMap[strategyType.getType()] = strategy
    }

    fun getStrategy(strategyType: StrategyType) : IStrategy<*>? {
        return strategyMap[strategyType.getType()]
    }
}