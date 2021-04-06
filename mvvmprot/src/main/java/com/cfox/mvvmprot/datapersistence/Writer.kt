package com.cfox.mvvmprot.datapersistence

class Writer {
    private lateinit var strategy : DataPersistStrategy

    fun setStrategy(strategy: DataPersistStrategy) {
        this.strategy  = strategy
    }

    fun putString(key: String, value: String?) {
        strategy.putString(key, value)
    }

    fun putStringSet(key: String, value: Set<String>?) {
        strategy.putStringSet(key, value)
    }

    fun putInt(key: String, value: Int) {
        strategy.putInt(key, value)
    }

    fun putLong(key: String, value: Long) {
        strategy.putLong(key, value)
    }

    fun putFloat(key: String, value: Float) {
        strategy.putFloat(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        strategy.putBoolean(key, value)
    }

    fun putMap(map: Map<String, *>) {
        strategy.putMap(map)
    }

    fun remove(key: String) {
        strategy.remove(key)
    }
}