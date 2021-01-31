package com.cfox.mvvmprot.base.eventstrategy.impl

import android.app.Activity
import com.cfox.mvvmprot.base.uievent.ActivityEvent
import com.cfox.mvvmprot.base.eventstrategy.IActivityStrategy

internal class DefaultActivityStrategy :
    IActivityStrategy {
    override fun execute(request: ActivityEvent) {
        val context = request.getContext()
        val intent = request.getActivityIntent()
        val startMode = request.getStartMode()
        context?.let { ctx ->
            intent?.let { intent ->
                if (startMode is ActivityEvent.StartMode.FOR_RESULT && ctx is Activity) {
                    ctx.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
                } else {
                    context.startActivity(intent)
                }
            }
        }
    }
}