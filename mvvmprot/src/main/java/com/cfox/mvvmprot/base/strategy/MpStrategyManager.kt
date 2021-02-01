package com.cfox.mvvmprot.base.strategy

import android.util.Log
import com.cfox.mvvmprot.BuildConfig
import com.cfox.mvvmprot.base.strategy.uievent.IUIEvent

internal class MpStrategyManager {

    private val strategyMap = mutableMapOf<Int, IStrategy<*>>()

    fun addStrategy(strategyType: StrategyType, strategy : IStrategy<*>) {
        if (BuildConfig.DEBUG) {
            strategyMap.forEach {
                if (it.key == strategyType.getType()) {
                    Log.e("TAG", "This StrategyType already exists ， key : ${strategyType.getType()}")
                }
            }
        }
        strategyMap[strategyType.getType()] = strategy
    }

    fun <T: IUIEvent> getStrategy(strategyType: StrategyType) : IStrategy<T>? {
        return strategyMap[strategyType.getType()] as IStrategy<T>
    }
}