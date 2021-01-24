package com.cfox.mvvmprot.base.eventstrategy.impl

import com.cfox.mvvmprot.base.uievent.IEventRequest
import com.cfox.mvvmprot.base.eventstrategy.IOtherStrategy

internal class DefaultOtherStrategy :
    IOtherStrategy {
    override fun execute(request: IEventRequest) {
        // do nothing
    }
}