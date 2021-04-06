package com.cfox.mvvmprot.support.strategy

import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.support.strategy.event.ActivityEvent

class DefActivityStrategy : IActivityStrategy<ActivityEvent> {
    override fun execute(event: ActivityEvent) {
        val startMode = event.getStartMode()
        val fragment = event.getFragment()
        val intent =  event.buildStartIntent()
        if (fragment != null) {
            if (startMode is ActivityEvent.StartMode.FOR_RESULT) {
                fragment.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
            } else {
                fragment.startActivity(intent)
            }
        } else {
            if (startMode is ActivityEvent.StartMode.FOR_RESULT) {
                event.getActivity().startActivityForResult(intent, startMode.requestCode, startMode.bundle)
            } else {
                event.getActivity().startActivity(intent)
            }
        }
    }
}