package com.cfox.mvvmprot.base.strategy.impl

import androidx.fragment.app.Fragment

import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy
import com.cfox.mvvmprot.base.strategy.impl.event.OrigFragmentEvent
import com.cfox.mvvmprot.base.strategy.impl.event.NavFragmentEvent

class DefFragmentStrategy : IFragmentStrategy<FragmentEvent> {

    /**
     * activity
     *        groupname
     *                  currentTag
     */
    private val fragmentTreeMap = mutableMapOf<String, MutableMap<String, String>>()

    override fun execute(request: FragmentEvent) {
        if (request is OrigFragmentEvent) {
            origAction(request)
        } else if (request is NavFragmentEvent) {
            navAction(request)
        }
    }

    private fun origAction(request: OrigFragmentEvent) {
        val fm = request.getFragmentManager()
        val ft = fm.beginTransaction()
        if (request.getRequestType() is OrigFragmentEvent.RequestType.SHOW) {
            val currentFragmentTag = getCurrentGroupFragmentTag(request)
            if (currentFragmentTag.isNotEmpty()) {
                val currentFragment = fm.findFragmentByTag(currentFragmentTag)
                currentFragment?.let {
                    ft.hide(it)
                }
            }

            val tag = request.getTag()
            var fragment: Fragment?
            if (tag.isNotEmpty()) {
                fragment = request.getFragmentManager().findFragmentByTag(tag)
                if (fragment != null) {
                    ft.show(fragment)
                } else {
                    fragment = request.getFragmentClass()?.newInstance() as Fragment?
                    ft.add(request.getContainerId(), fragment!!, tag)
                    ft.commitAllowingStateLoss()
                }
                setCurrentGroupFragmentTag(request)
            }
        }
    }

    private fun getCurrentGroupFragmentTag(request: OrigFragmentEvent): String {
        var result: String = ""
        val rootGroup = fragmentTreeMap[request.getActivityName()]
        if (rootGroup != null) {
            result = rootGroup[request.getGroupName()] ?: ""
        }
        return result
    }

    private fun setCurrentGroupFragmentTag(request: OrigFragmentEvent) {
        val rootGroup = fragmentTreeMap[request.getActivityName()]
        if (rootGroup != null) {
            rootGroup[request.getGroupName()] = request.getTag()
        } else {
            fragmentTreeMap[request.getActivityName()] =
                mutableMapOf(request.getGroupName() to request.getTag())
        }
    }

    private fun navAction(request: NavFragmentEvent) {

        request.getNavController()?.let {
            it.navigate(request.actionId)
        }

    }

    override fun onActivityDestroy(activityName: String) {
        fragmentTreeMap.remove(activityName)
    }

}