package com.cfox.appdemo.strategy

import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.impl.event.AppActivityEvent

class AppActivityStrategy : IActivityStrategy<AppActivityEvent> {

    override fun execute(event: AppActivityEvent) {

    }
}