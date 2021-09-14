package com.cfox.mvvmprot.base.strategy

import android.util.Log
import com.cfox.mvvmprot.BuildConfig
import com.cfox.mvvmprot.base.strategy.uievent.IUIEvent

internal class MPStrategyManager {

    private val strategyMap = mutableMapOf<Int, IStrategy<*>>()

    fun addStrategy(strategyType: StrategyType, strategy : IStrategy<*>) {
        if (BuildConfig.DEBUG) {
            Log.d("MpStrategyManager", "addStrategy， type : ${strategyType.getType()}")
        }
        strategyMap[strategyType.getType()] = strategy
    }

    fun <T: IUIEvent> getStrategy(strategyType: StrategyType) : IStrategy<T>? {
        val strategy = strategyMap[strategyType.getType()]
        return strategy?.let {
            it as IStrategy<T>
        }
    }
}