package com.cfox.mvvmprot.datapersistence

import android.content.Context

class EmptyDataPersistStrategy : DataPersistStrategy("empty") {
    override fun onCreate(context: Context) {

    }

    override fun getString(key: String, defaultValue: String?): String? = null

    override fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? = null

    override fun getInt(key: String, defaultValue: Int): Int = 0

    override fun getLong(key: String, defaultValue: Long): Long = 0L

    override fun getFloat(key: String, defaultValue: Float): Float = 0.0f

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = false

    override fun putString(key: String, value: String?) {}

    override fun putStringSet(key: String, value: Set<String>?) {}

    override fun putInt(key: String, value: Int) {}

    override fun putLong(key: String, value: Long) {}

    override fun putFloat(key: String, value: Float) {}

    override fun putBoolean(key: String, value: Boolean) {}

    override fun putMap(map: Map<String, *>) {}

    override fun remove(key: String) {}
}