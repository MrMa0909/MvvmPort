package com.cfox.mvvmprot.datapersistence

class DataPersistManager {

    private var strategy : IDataPersistStrategy = DefaultDataPersistStrategy()

    fun setStrategy(strategy: IDataPersistStrategy) {
        this.strategy = strategy
    }

    fun getStrategy() : IDataPersistStrategy {
        return strategy
    }
}