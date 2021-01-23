package com.cfox.mvvmprot.datapersistence

import android.content.Context

class DataPersistManager {

    private var strategy : IDataPersistStrategy = DefaultDataPersistStrategy()

    fun init(context: Context) {
        strategy.onCreate(context)
    }

    fun setStrategy(strategy: IDataPersistStrategy) {
        this.strategy = strategy
    }

    fun getStrategy() : IDataPersistStrategy {
        return strategy
    }
}