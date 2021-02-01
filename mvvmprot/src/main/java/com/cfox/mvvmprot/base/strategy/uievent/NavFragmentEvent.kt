package com.cfox.mvvmprot.base.strategy.uievent

open class NavFragmentEvent : FragmentEvent() {

    sealed class RequestType {
        class START(val fragmentClass: Class<*>) : RequestType()
        object BACK : RequestType()
    }
}