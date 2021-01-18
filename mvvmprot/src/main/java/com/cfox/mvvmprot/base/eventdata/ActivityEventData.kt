package com.cfox.mvvmprot.base.eventdata

import android.app.Activity
import android.content.Context
import android.content.Intent

open class ActivityEventData : IEventData {
    private var content : Context ? = null
    private var intent : Intent ? = null

    internal fun setContext(context: Context?) {
        this.content = context
    }

    fun getContext() : Context ? {
        if (content !is Activity) {
            content = null
        }
        return content
    }

    fun setActivityIntent(intent: Intent) {
        this.intent = intent
    }

    fun getActivityIntent() : Intent? {
        return intent
    }
}