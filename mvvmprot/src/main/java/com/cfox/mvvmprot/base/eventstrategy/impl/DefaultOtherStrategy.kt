package com.cfox.mvvmprot.base.eventstrategy.impl

import com.cfox.mvvmprot.base.eventdata.IEventData
import com.cfox.mvvmprot.base.eventstrategy.IOtherStrategy

internal class DefaultOtherStrategy :
    IOtherStrategy {
    override fun execute(data: IEventData) {
        // do nothing
    }
}