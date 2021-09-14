package com.cfox.mvvmprot.base.strategy.uievent

import com.cfox.mvvmprot.base.strategy.IOtherStrategy

abstract class AbsOtherStrategy<T : IUIEvent> : IOtherStrategy<IUIEvent> {

    override fun execute(event: IUIEvent) {
        onExecute(event as T)
    }
    abstract fun onExecute(event : T)
}