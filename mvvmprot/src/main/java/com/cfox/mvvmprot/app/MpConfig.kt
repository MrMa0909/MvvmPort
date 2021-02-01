package com.cfox.mvvmprot.app

import android.content.Context
import com.cfox.mvvmprot.base.strategy.*
import com.cfox.mvvmprot.datapersistence.DataPersistManager
import com.cfox.mvvmprot.datapersistence.IDataPersistStrategy
import com.trello.rxlifecycle4.android.ActivityEvent

class MpConfig {
    private val strategyManager = MpStrategyManager()
    private val dataPersistManager = DataPersistManager()

    private var isInit = false
    private constructor() {}

    internal fun init(context: Context) {
        if (!isInit) {
            isInit = true
            dataPersistManager.init(context)
        }
    }

    private constructor(builder: Builder) {
        builder.activityStrategy?.let {
            strategyManager.addStrategy(StrategyType.ACTIVITY, it)
        }
        builder.fragmentStrategy?.let {
            strategyManager.addStrategy(StrategyType.FRAGMENT, it)
        }
        builder.dialogStrategy?.let {
            strategyManager.addStrategy(StrategyType.DIALOG, it)
        }
        builder.otherStrategy?.let {
            strategyManager.addStrategy(StrategyType.OTHER, it)
        }
        builder.dataPersistStrategy?.let {
            dataPersistManager.setStrategy(it)
        }
    }

    internal fun getStrategyManager() : MpStrategyManager {
        return strategyManager
    }

    internal fun getDataPersistManager() : DataPersistManager {
        return dataPersistManager
    }

    class Builder {
        var activityStrategy: IActivityStrategy<*> ? = null
        var fragmentStrategy: IFragmentStrategy<*> ? = null
        var dialogStrategy: IDialogStrategy<*> ? = null
        var otherStrategy: IOtherStrategy<*> ? = null
        var dataPersistStrategy : IDataPersistStrategy? = null
        fun build() : MpConfig {
            return MpConfig(this)
        }
    }
}