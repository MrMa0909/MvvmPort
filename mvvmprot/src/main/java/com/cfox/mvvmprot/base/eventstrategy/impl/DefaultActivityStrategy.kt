package com.cfox.mvvmprot.base.eventstrategy.impl

import android.app.Activity
import com.cfox.mvvmprot.base.uievent.ActivityEventRequest
import com.cfox.mvvmprot.base.eventstrategy.IActivityStrategy

internal class DefaultActivityStrategy :
    IActivityStrategy {
    override fun execute(request: ActivityEventRequest) {
        val context = request.getContext()
        val intent = request.getActivityIntent()
        val startMode = request.getStartMode()
        context?.let { ctx ->
            intent?.let { intent ->
                if (startMode is ActivityEventRequest.StartMode.FOR_RESULT && ctx is Activity) {
                    ctx.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
                } else {
                    context.startActivity(intent)
                }
            }
        }
    }
}