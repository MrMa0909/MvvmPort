package com.cfox.mvvmprot.base.strategy.uievent

import com.cfox.mvvmprot.base.strategy.IActivityStrategy

abstract class AbsActivityStrategy<T : AbsActivityEvent> : IActivityStrategy<AbsActivityEvent> {

    override fun execute(event: AbsActivityEvent) {
        onExecute(event as T)
    }

    abstract fun onExecute(event : T)

}