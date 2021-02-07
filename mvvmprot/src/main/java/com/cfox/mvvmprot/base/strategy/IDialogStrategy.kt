package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.AbsDialogEvent

interface IDialogStrategy<T : AbsDialogEvent> : IStrategy<T> {
}