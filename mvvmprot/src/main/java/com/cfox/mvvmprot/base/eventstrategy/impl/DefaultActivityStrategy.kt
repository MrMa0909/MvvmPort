package com.cfox.mvvmprot.base.eventstrategy.impl

import android.app.Activity
import com.cfox.mvvmprot.base.eventdata.ActivityEventData
import com.cfox.mvvmprot.base.eventstrategy.IActivityStrategy

internal class DefaultActivityStrategy :
    IActivityStrategy {
    override fun execute(data: ActivityEventData) {
        val context = data.getContext()
        val intent = data.getActivityIntent()
        val startMode = data.getStartMode()
        context?.let { ctx ->
            intent?.let { intent ->
                if (startMode is ActivityEventData.StartMode.FOR_RESULT && ctx is Activity) {
                    ctx.startActivityForResult(intent, startMode.requestCode, startMode.bundle)
                } else {
                    context.startActivity(intent)
                }
            }
        }
    }
}