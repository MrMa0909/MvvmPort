package com.cfox.mvvmprot.base.eventdata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

open class ActivityEventData : IEventData {

    private var startMode : StartMode = StartMode.DEFAULT

    private var content : Context ? = null
    private var intent : Intent ? = null
    private var cls : Class<*> ? = null
    private var className : String ? = null
    private var intentFlags: Int ? = null
    private var intentExtras: Bundle ? = null
    private var params: List<Pair<String, Any>?> ? = null

    private constructor(){}

    private constructor(startMode: StartMode = StartMode.DEFAULT) {
        this.startMode = startMode
    }

    constructor(cls : Class<*> , startMode: StartMode = StartMode.DEFAULT):this (startMode) {
        this.cls = cls
    }

    constructor(className : String , startMode: StartMode = StartMode.DEFAULT):this (startMode) {
        this.className = className
    }

    internal fun setContext(context: Context?) {
        this.content = context
    }

    fun getContext() : Context ? {
        return content
    }

    fun setParams(params : List<Pair<String, Any>?>) {
        this.params = params
    }

    internal fun buildStartIntent() {
        intent = createIntent()
        cls?.let {
            intent?.setClass(content!!, it)
        }

        className?.let {
            intent?.setClassName(content!!, it)
        }

        addParamsToIntent()

    }

    private fun createIntent(): Intent {
        return Intent().apply {
            intentFlags?.let {
                setFlags(it)
            }

            intentExtras?.let {
                putExtras(it)
            }
        }
    }

    private fun addParamsToIntent() {
        intent?.apply {
            params?.let {
                for (pair in it)
                    pair?.let {
                        val name = pair.first
                        when (val value = pair.second) {
                            is Int -> putExtra(name, value)
                            is Byte -> putExtra(name, value)
                            is Char -> putExtra(name, value)
                            is Short -> putExtra(name, value)
                            is Boolean -> putExtra(name, value)
                            is Long -> putExtra(name, value)
                            is Float -> putExtra(name, value)
                            is Double -> putExtra(name, value)
                            is String -> putExtra(name, value)
                            is CharSequence -> putExtra(name, value)
                            is Parcelable -> putExtra(name, value)
                            is Array<*> -> putExtra(name, value)
                            is ArrayList<*> -> putExtra(name, value)
                            is Serializable -> putExtra(name, value)
                            is BooleanArray -> putExtra(name, value)
                            is ByteArray -> putExtra(name, value)
                            is ShortArray -> putExtra(name, value)
                            is CharArray -> putExtra(name, value)
                            is IntArray -> putExtra(name, value)
                            is LongArray -> putExtra(name, value)
                            is FloatArray -> putExtra(name, value)
                            is DoubleArray -> putExtra(name, value)
                            is Bundle -> putExtra(name, value)
                            is Intent -> putExtra(name, value)
                            else -> {
                            }
                        }
                    }
            }
        }
    }

    fun getActivityIntent() : Intent? {
        return intent
    }

    fun getStartMode() : StartMode {
        return startMode
    }


    sealed class StartMode {
        object DEFAULT : StartMode()
        class FOR_RESULT(val requestCode : Int, val bundle: Bundle ? = null) : StartMode()
    }
}