package com.cfox.mvvmprot.base.strategy.impl.event

import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent

class OrigFragmentEvent(private val requestType: RequestType, private val groupName: String = "") : FragmentEvent() {

    fun addBackStack() {

    }

    fun getRequestType() : RequestType {
        return requestType
    }

    fun getGroupName() : String {
        var gName = requestType.containerId.toString()
        if (groupName.isNotEmpty()) {
            gName = groupName
        }
        return gName
    }

    fun getContainerId() : Int {
        return requestType.containerId
    }

    fun getTag() : String {
        return requestType.fcls?.name ?: ""
    }

    fun getFragmentClass() : Class<*>? {
        return requestType.fcls
    }

    sealed class RequestType(val containerId : Int, val fcls: Class<*>?) {
        class SHOW(containerId : Int, fragmentClass: Class<*>) : RequestType(containerId, fragmentClass)
        class BACK(containerId : Int) : RequestType(containerId , null)
    }
}