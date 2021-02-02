package com.cfox.mvvmprot.base.strategy.impl

import android.app.Activity
import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.impl.event.AppActivityEvent

class DefActivityStrategy : IActivityStrategy<AppActivityEvent> {
    override fun execute(event: AppActivityEvent) {
        val context = event.getContext()
        val startMode = event.getStartMode()
        context?.let { ctx ->
            val intent =  event.buildStartIntent()
            if (startMode is AppActivityEvent.StartMode.FOR_RESULT && ctx is Activity) {
                ctx.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
            } else {
                context.startActivity(intent)
            }
        }
    }
}