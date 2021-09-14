package com.cfox.mvvmprot.base.strategy.uievent

import com.cfox.mvvmprot.base.strategy.IFragmentStrategy


abstract class AbsFragmentStrategy<T : AbsFragmentEvent> : IFragmentStrategy<AbsFragmentEvent> {

    override fun execute(event: AbsFragmentEvent) {
        onExecute(event as T)
    }

    abstract fun onExecute(event : T)

}