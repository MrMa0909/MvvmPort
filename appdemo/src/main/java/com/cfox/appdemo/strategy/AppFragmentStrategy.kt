package com.cfox.appdemo.strategy

import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy

class AppFragmentStrategy : IFragmentStrategy<FragmentEvent> {
    override fun onActivityDestroy(activityName: String) {

    }

    override fun execute(event: FragmentEvent) {

    }


}