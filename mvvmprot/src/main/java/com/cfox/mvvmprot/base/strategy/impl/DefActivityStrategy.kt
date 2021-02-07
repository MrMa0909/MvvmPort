package com.cfox.mvvmprot.base.strategy.impl

import android.app.Activity
import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.impl.event.ActivityEvent

class DefActivityStrategy : IActivityStrategy<ActivityEvent> {
    override fun execute(event: ActivityEvent) {
        val context = event.getContext()
        val startMode = event.getStartMode()
        context?.let { ctx ->
            val intent =  event.buildStartIntent()
            if (startMode is ActivityEvent.StartMode.FOR_RESULT && ctx is Activity) {
                ctx.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
            } else {
                context.startActivity(intent)
            }
        }
    }
}