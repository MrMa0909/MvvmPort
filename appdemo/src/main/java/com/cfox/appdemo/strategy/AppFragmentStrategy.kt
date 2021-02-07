package com.cfox.appdemo.strategy

import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy

class AppFragmentStrategy : IFragmentStrategy<AbsFragmentEvent> {
    override fun onActivityDestroy(activityName: String) {

    }

    override fun execute(event: AbsFragmentEvent) {

    }


}