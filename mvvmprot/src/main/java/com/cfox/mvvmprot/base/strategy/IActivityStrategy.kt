package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.ActivityEvent

interface IActivityStrategy<T : ActivityEvent> :
    IStrategy<T> {
}