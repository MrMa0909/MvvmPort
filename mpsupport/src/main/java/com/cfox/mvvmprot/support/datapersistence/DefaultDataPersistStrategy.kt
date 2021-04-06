package com.cfox.mvvmprot.support.datapersistence

import android.content.Context
import com.cfox.mvvmprot.datapersistence.DataPersistStrategy
import com.tencent.mmkv.MMKV

internal class DefaultDataPersistStrategy : DataPersistStrategy(NAME) {

    companion object {
        const val NAME = "default"
    }

    private lateinit var kv : MMKV
    override fun onCreate(context: Context) {
        MMKV.initialize(context)
        kv = MMKV.defaultMMKV()!!
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return kv.decodeString(key,  defaultValue)
    }

    override fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        return kv.decodeStringSet(key, defaultValue)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return kv.decodeInt(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return kv.decodeLong(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return kv.decodeFloat(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return kv.decodeBool(key, defaultValue)
    }

    override fun putString(key: String, value: String?) {
        kv.encode(key, value)
    }

    override fun putStringSet(key: String, value: Set<String>?) {
        kv.encode(key, value)
    }

    override fun putInt(key: String, value: Int) {
        kv.encode(key, value)
    }

    override fun putLong(key: String, value: Long) {
        kv.encode(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        kv.encode(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        kv.encode(key, value)
    }

    override fun putMap(map: Map<String, *>) {
        map.forEach {
            val value = it.value
            val key = it.key
            when(value) {
                is String -> kv.encode(key, value)
                is Int -> kv.encode(key, value)
                is Long -> kv.encode(key, value)
                is Float -> kv.encode(key, value)
                is Boolean -> kv.encode(key, value)
            }
        }
    }

    override fun remove(key: String) {
        kv.removeValueForKey(key)
    }

}