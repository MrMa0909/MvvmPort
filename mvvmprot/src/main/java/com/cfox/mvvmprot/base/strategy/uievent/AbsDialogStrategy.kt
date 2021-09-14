package com.cfox.mvvmprot.base.strategy.uievent

import com.cfox.mvvmprot.base.strategy.IDialogStrategy

abstract class AbsDialogStrategy<T : AbsDialogEvent> : IDialogStrategy<AbsDialogEvent> {

    override fun execute(event: AbsDialogEvent) {
        onExecute(event as T)
    }

    abstract fun onExecute(event : T)
}