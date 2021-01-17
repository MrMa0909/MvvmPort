package com.cfox.mvvmprot.base.eventstrategy

import android.util.Log
import com.cfox.mvvmprot.BuildConfig

class MpStrategyManager {

    private val strategyMap = mutableMapOf<Int, IStrategy<*>>()

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