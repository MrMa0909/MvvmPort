package com.cfox.appdemo.strategy

import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.impl.event.ActivityEvent

class AppActivityStrategy : IActivityStrategy<ActivityEvent> {

    override fun execute(event: ActivityEvent) {

    }
}