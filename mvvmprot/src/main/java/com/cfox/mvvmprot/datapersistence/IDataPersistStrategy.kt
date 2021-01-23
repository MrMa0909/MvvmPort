package com.cfox.mvvmprot.datapersistence

interface IDataPersistStrategy {

    fun getString(key: String, defaultValue: String ? = null) : String ?

    fun getStringSet(key: String, defaultValue: Set<String> ? = null) : Set<String> ?

    fun getInt(key: String, defaultValue: Int ? = null) : Int ?

    fun getLong(key: String, defaultValue: Long ? = null) : Long ?

    fun getFloat(key: String, defaultValue: Float ? = null) : Float ?

    fun getBoolean(key: String, defaultValue: Boolean ? = null) : Boolean ?


    fun putString(key: String, value: String)

    fun putStringSet(key: String, value: Set<String>)

    fun putInt(key: String, value: Int)

    fun putLong(key: String, value: Long)

    fun putFloat(key: String, value: Float)

    fun putBoolean(key: String, value: Boolean)

    fun putMap(map : Map<String, *>)

    fun remove(key: String)

}