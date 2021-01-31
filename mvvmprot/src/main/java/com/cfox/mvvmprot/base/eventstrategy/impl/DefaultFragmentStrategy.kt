package com.cfox.mvvmprot.base.eventstrategy.impl

import androidx.fragment.app.Fragment
import com.cfox.mvvmprot.base.uievent.FragmentEvent
import com.cfox.mvvmprot.base.eventstrategy.IFragmentStrategy
import com.cfox.mvvmprot.base.uievent.NavFragmentEvent
import com.cfox.mvvmprot.base.uievent.OrigFragmentEvent

internal class DefaultFragmentStrategy : IFragmentStrategy {
    override fun execute(request: FragmentEvent) {
        if (request is OrigFragmentEvent)  {
            origAction(request)
        } else if (request is NavFragmentEvent) {
            navAction(request)
        }
    }

    private fun origAction(request: OrigFragmentEvent) {

        if (request.requestType is OrigFragmentEvent.RequestType.START) {
            val tag = request.getTag()
            var fragment : Fragment? = null
            if (tag.isNotEmpty()) {
                fragment = request.getFragmentManager().findFragmentByTag(tag)
            }

            if (fragment == null) {
                fragment = request.getFragmentClass()?.newInstance() as Fragment?
            }



        }




    }

    private fun navAction(request : NavFragmentEvent) {

    }
}