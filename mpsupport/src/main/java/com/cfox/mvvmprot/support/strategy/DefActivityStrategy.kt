package com.cfox.mvvmprot.support.strategy

import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.support.strategy.event.ActivityEvent

class DefActivityStrategy : IActivityStrategy<ActivityEvent> {
    override fun execute(event: ActivityEvent) {
        val startMode = event.getStartMode()
        val fragment = event.getFragment()
        event.buildStartIntent()?.let {
            if (fragment != null) {
                if (startMode is ActivityEvent.StartMode.FOR_RESULT) {
                    fragment.startActivityForResult(it, startMode.requestCode, startMode.bundle)
                } else {
                    fragment.startActivity(it)
                }
            } else {
                if (startMode is ActivityEvent.StartMode.FOR_RESULT) {
                    event.getActivity()?.startActivityForResult(it, startMode.requestCode, startMode.bundle)
                } else {
                    event.getActivity()?.startActivity(it)
                }
            }
        }
    }
}