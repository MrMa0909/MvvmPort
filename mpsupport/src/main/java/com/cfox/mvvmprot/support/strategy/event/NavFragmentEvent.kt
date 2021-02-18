package com.cfox.mvvmprot.support.strategy.event

import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent

class NavFragmentEvent(private val navHostId: Int, private val eventType: NavEventType) : AbsFragmentEvent() {

    fun getEventType() : NavEventType {
        return eventType
    }

    fun getNavHostId() : Int {
        return navHostId
    }

    sealed class NavEventType {
        class NAVIGATE(val actionId: Int) : NavEventType()
        object NAVIGATE_UP : NavEventType()
    }
}