package com.cfox.mvvmprot.base.strategy.uievent

import android.content.Context

abstract class ActivityEvent : IUIEvent {

    private var content : Context ? = null

    internal fun setContext(context: Context?) {
        this.content = context
    }

    fun getContext() : Context ? {
        return content
    }
}