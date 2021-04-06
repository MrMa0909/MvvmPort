package com.cfox.mvvmprot.datapersistence

import android.content.Context

internal class DataPersistManager {

    private val strategyMap = mutableMapOf<String, DataPersistStrategy>()

    fun init(context: Context) {
        strategyMap.forEach {
            it.value.onCreate(context)
        }
    }

    fun addStrategy(strategy: DataPersistStrategy) {
        this.strategyMap[strategy.name] = strategy
    }

    fun getStrategy(name: String) : DataPersistStrategy {
        return this.strategyMap[name] ?: EmptyDataPersistStrategy()
    }
}