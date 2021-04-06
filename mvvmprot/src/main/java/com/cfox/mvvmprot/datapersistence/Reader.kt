package com.cfox.mvvmprot.datapersistence

class Reader {

    private lateinit var strategy : DataPersistStrategy

    fun setStrategy(strategy: DataPersistStrategy) {
        this.strategy  = strategy
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return strategy.getString(key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>? {
        return strategy.getStringSet(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return strategy.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return strategy.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float  {
        return strategy.getFloat(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return strategy.getBoolean(key, defaultValue)
    }

}