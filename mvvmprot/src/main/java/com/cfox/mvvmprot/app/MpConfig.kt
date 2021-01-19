package com.cfox.mvvmprot.app

import android.content.Context
import com.cfox.mvvmprot.base.eventstrategy.*
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultActivityStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultDialogStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultFragmentStrategy
import com.cfox.mvvmprot.base.eventstrategy.impl.DefaultOtherStrategy
import com.cfox.mvvmprot.datapersistence.DataPersistManager
import com.cfox.mvvmprot.datapersistence.DefaultDataPersistStrategy
import com.cfox.mvvmprot.datapersistence.IDataPersistStrategy

class MpConfig {
    private val strategyManager = MpStrategyManager()
    private val dataPersistManager = DataPersistManager()

    private constructor() {}

    private constructor(builder: Builder) {
        strategyManager.setStrategy(StrategyType.ACTIVITY, builder.activityStrategy ?: DefaultActivityStrategy())
        strategyManager.setStrategy(StrategyType.FRAGMENT, builder.fragmentStrategy ?: DefaultFragmentStrategy())
        strategyManager.setStrategy(StrategyType.DIALOG, builder.dialogStrategy ?: DefaultDialogStrategy())
        strategyManager.setStrategy(StrategyType.OTHER, builder.otherStrategy ?: DefaultOtherStrategy())

        dataPersistManager.setStrategy(builder.dataPersistStrategy ?: DefaultDataPersistStrategy())
    }

    fun getStrategyManager() : MpStrategyManager {
        return strategyManager
    }

    internal fun init(context: Context) {
        
    }

    class Builder {
        var activityStrategy: IActivityStrategy ? = null
        var fragmentStrategy: IFragmentStrategy ? = null
        var dialogStrategy: IDialogStrategy ? = null
        var otherStrategy: IOtherStrategy ? = null
        var dataPersistStrategy : IDataPersistStrategy? = null
        fun build() : MpConfig {
            return MpConfig(this)
        }
    }
}