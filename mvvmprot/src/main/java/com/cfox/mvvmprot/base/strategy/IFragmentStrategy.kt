package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent

interface IFragmentStrategy <T : FragmentEvent>: IStrategy<T> {

//    fun
    fun onActivityDestroy(activityName : String)
}