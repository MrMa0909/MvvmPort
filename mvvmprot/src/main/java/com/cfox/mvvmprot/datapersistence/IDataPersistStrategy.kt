package com.cfox.mvvmprot.datapersistence

import android.content.Context

interface IDataPersistStrategy {

    fun onCreate(context: Context)

    fun getString(key: String, defaultValue: String?) : String ?

    fun getStringSet(key: String, defaultValue: Set<String> ?) : Set<String> ?

    fun getInt(key: String, defaultValue: Int) : Int

    fun getLong(key: String, defaultValue: Long) : Long

    fun getFloat(key: String, defaultValue: Float) : Float

    fun getBoolean(key: String, defaultValue: Boolean) : Boolean


    fun putString(key: String, value: String?)

    fun putStringSet(key: String, value: Set<String>?)

    fun putInt(key: String, value: Int)

    fun putLong(key: String, value: Long)

    fun putFloat(key: String, value: Float)

    fun putBoolean(key: String, value: Boolean)

    fun putMap(map : Map<String, *>)

    fun remove(key: String)

}