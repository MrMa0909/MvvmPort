package com.cfox.mvvmprot.base.strategy

import com.cfox.mvvmprot.base.strategy.uievent.IUIEvent

interface IOtherStrategy<T : IUIEvent> : IStrategy<T> {
}