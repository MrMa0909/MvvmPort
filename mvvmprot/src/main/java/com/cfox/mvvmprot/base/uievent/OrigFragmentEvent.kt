package com.cfox.mvvmprot.base.uievent

import androidx.fragment.app.FragmentManager

open class OrigFragmentEvent(val requestType: RequestType) : FragmentEvent() {

    private lateinit var fragmentManager : FragmentManager

    internal fun setFragmentManager(fragmentManager : FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun getFragmentManager() : FragmentManager {
        return fragmentManager
    }

    fun getContainerId() : Int {
        return 0
    }

    fun addBackStack() {

    }

    fun getTag() : String {
        return requestType.fcls?.simpleName ?: ""
    }

    fun getFragmentClass() : Class<*>? {
        return requestType.fcls
    }

    sealed class RequestType(val fcls: Class<*>?) {
        class START(val fragmentClass: Class<*>) : RequestType(fragmentClass)
        object BACK : RequestType(null)
    }

}