package com.cfox.mvvmprot.support.strategy.event

import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent

class OrigFragmentEvent(private val eventType: OrigEventType, private val groupName: String = "") : AbsFragmentEvent() {

    fun addBackStack() {

    }

    fun getEventType() : OrigEventType {
        return eventType
    }

    fun getGroupName() : String {
        var gName = eventType.containerId.toString()
        if (groupName.isNotEmpty()) {
            gName = groupName
        }
        return gName
    }

    fun getContainerId() : Int {
        return eventType.containerId
    }

    fun getTag() : String {
        return eventType.fcls?.name ?: ""
    }

    fun getFragmentClass() : Class<*>? {
        return eventType.fcls
    }

    sealed class OrigEventType(val containerId : Int, val fcls: Class<*>?) {
        class SHOW(containerId : Int, fragmentClass: Class<*>) : OrigEventType(containerId, fragmentClass)
        class BACK(containerId : Int) : OrigEventType(containerId , null)
    }
}