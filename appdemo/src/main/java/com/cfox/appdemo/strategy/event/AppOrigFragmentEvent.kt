package com.cfox.appdemo.strategy.event

import com.cfox.mvvmprot.base.strategy.uievent.OrigFragmentEvent

class AppOrigFragmentEvent(val requestType: RequestType, val groupName: String = "fcu") : OrigFragmentEvent() {

    fun addBackStack() {

    }

    fun getTag() : String {
        return requestType.fcls?.simpleName ?: ""
    }

    fun getFragmentClass() : Class<*>? {
        return requestType.fcls
    }

    sealed class RequestType(val fcls: Class<*>?) {
        class SHOW(val fragmentClass: Class<*>, val containerId : Int) : RequestType(fragmentClass)
        object BACK : RequestType(null)
    }
}