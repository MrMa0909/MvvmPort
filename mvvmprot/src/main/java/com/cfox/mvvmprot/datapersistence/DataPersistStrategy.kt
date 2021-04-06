package com.cfox.mvvmprot.datapersistence

import android.content.Context

abstract class DataPersistStrategy(val name : String) {

    abstract fun onCreate(context: Context)

    abstract fun getString(key: String, defaultValue: String?) : String ?

    abstract fun getStringSet(key: String, defaultValue: Set<String> ?) : Set<String> ?

    abstract fun getInt(key: String, defaultValue: Int) : Int

    abstract fun getLong(key: String, defaultValue: Long) : Long

    abstract fun getFloat(key: String, defaultValue: Float) : Float

    abstract fun getBoolean(key: String, defaultValue: Boolean) : Boolean


    abstract fun putString(key: String, value: String?)

    abstract fun putStringSet(key: String, value: Set<String>?)

    abstract fun putInt(key: String, value: Int)

    abstract fun putLong(key: String, value: Long)

    abstract fun putFloat(key: String, value: Float)

    abstract fun putBoolean(key: String, value: Boolean)

    abstract fun putMap(map : Map<String, *>)

    abstract fun remove(key: String)

}