package com.cfox.appdemo.strategy

import androidx.fragment.app.Fragment
import com.cfox.appdemo.strategy.event.AppOrigFragmentEvent
import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy
import com.cfox.mvvmprot.base.strategy.uievent.NavFragmentEvent

class AppFragmentStrategy : IFragmentStrategy<FragmentEvent> {

    /**
     * activity
     *        groupname
     *                  currentTag
     */
    private val fragmentTreeMap = mutableMapOf<String, MutableMap<String, String>>()

    override fun execute(request: FragmentEvent) {
        if (request is AppOrigFragmentEvent)  {
            origAction(request)
        } else if (request is NavFragmentEvent) {
            navAction(request)
        }
    }

    private fun origAction(request: AppOrigFragmentEvent) {
        val fm = request.getFragmentManager()
        val ft = fm.beginTransaction()
        if (request.requestType is AppOrigFragmentEvent.RequestType.SHOW) {
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
                    ft.add(request.requestType.containerId, fragment!!, tag)
                }
                setCurrentGroupFragmentTag(request)
            }
        }
    }

    private fun getCurrentGroupFragmentTag(request: AppOrigFragmentEvent): String {
        var result : String = ""
        val rootGroup = fragmentTreeMap[request.getActivityName()]
        if (rootGroup != null) {
            result = rootGroup[request.groupName] ?: ""
        }
        return result
    }

    private fun setCurrentGroupFragmentTag(request: AppOrigFragmentEvent) {
        val rootGroup = fragmentTreeMap[request.getActivityName()]
        if (rootGroup != null) {
            rootGroup[request.groupName] = request.getTag()
        } else {
            fragmentTreeMap[request.getActivityName()]  = mutableMapOf(request.groupName to request.getTag())
        }
    }

    private fun navAction(request : NavFragmentEvent) {

    }

    override fun onActivityDestroy(activityName: String) {
        fragmentTreeMap.remove(activityName)
    }

}