package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent

interface IFragmentStrategy <T : AbsFragmentEvent>: IStrategy<T> {

//    fun
    fun onActivityDestroy(activityName : String)
}