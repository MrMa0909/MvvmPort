package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.AbsActivityEvent

interface IActivityStrategy<T : AbsActivityEvent> : IStrategy<T> {
}