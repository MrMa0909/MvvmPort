package com.cfox.mvvmprot.datapersistence

import com.cfox.mvvmprot.app.MPort

object DataPersist  {

    private val dataPersistManager = MPort.getConfig().getDataPersistManager()
    fun getString(key: String, defaultValue: String? = null): String? {
        return dataPersistManager.getStrategy().getString(key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>? {
        return dataPersistManager.getStrategy().getStringSet(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return dataPersistManager.getStrategy().getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return dataPersistManager.getStrategy().getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float  {
        return dataPersistManager.getStrategy().getFloat(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return dataPersistManager.getStrategy().getBoolean(key, defaultValue)
    }

    fun putString(key: String, value: String?) {
        dataPersistManager.getStrategy().putString(key, value)
    }

    fun putStringSet(key: String, value: Set<String>?) {
        dataPersistManager.getStrategy().putStringSet(key, value)
    }

    fun putInt(key: String, value: Int) {
        dataPersistManager.getStrategy().putInt(key, value)
    }

    fun putLong(key: String, value: Long) {
        dataPersistManager.getStrategy().putLong(key, value)
    }

    fun putFloat(key: String, value: Float) {
        dataPersistManager.getStrategy().putFloat(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        dataPersistManager.getStrategy().putBoolean(key, value)
    }

    fun putMap(map: Map<String, *>) {
        dataPersistManager.getStrategy().putMap(map)
    }

    fun remove(key: String) {
        dataPersistManager.getStrategy().remove(key)
    }

}