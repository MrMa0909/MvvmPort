package com.cfox.mvvmprot.base.strategy

import android.util.Log
import com.cfox.mvvmprot.BuildConfig
import com.cfox.mvvmprot.base.strategy.impl.DefActivityStrategy
import com.cfox.mvvmprot.base.strategy.impl.DefFragmentStrategy
import com.cfox.mvvmprot.base.strategy.uievent.IUIEvent

internal class MpStrategyManager {

    private val strategyMap = mutableMapOf<Int, IStrategy<*>>()

    init {
        strategyMap[StrategyType.ACTIVITY.getType()] = DefActivityStrategy()
        strategyMap[StrategyType.FRAGMENT.getType()] = DefFragmentStrategy()
//        strategyMap[StrategyType.DIALOG.getType()] = DefDialogStrategy()
//        strategyMap[StrategyType.OTHER.getType()] = DefOtherStrategy()
    }


    fun addStrategy(strategyType: StrategyType, strategy : IStrategy<*>) {
        if (BuildConfig.DEBUG) {
            Log.d("MpStrategyManager", "addStrategy， type : ${strategyType.getType()}")
        }
        strategyMap[strategyType.getType()] = strategy
    }

    fun <T: IUIEvent> getStrategy(strategyType: StrategyType) : IStrategy<T>? {
        return strategyMap[strategyType.getType()] as IStrategy<T>
    }
}