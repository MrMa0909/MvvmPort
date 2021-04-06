package com.cfox.mvvmprot.support.strategy

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import com.cfox.mvvmprot.base.strategy.uievent.AbsFragmentEvent
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy
import com.cfox.mvvmprot.support.strategy.event.OrigFragmentEvent
import com.cfox.mvvmprot.support.strategy.event.NavFragmentEvent

class DefFragmentStrategy : IFragmentStrategy<AbsFragmentEvent> {

    companion object {
        private const val TAG = "DefFragmentStrategy"
    }

    /**
     * activity
     *        groupname
     *                  currentTag
     */
    private val fragmentTreeMap = mutableMapOf<String, MutableMap<String, String>>()

    override fun execute(event: AbsFragmentEvent) {
        if (event is OrigFragmentEvent) {
            origAction(event)
        } else if (event is NavFragmentEvent) {
            navAction(event)
        }
    }

    private fun origAction(request: OrigFragmentEvent) {
        val fm = request.getFragmentManager()
        val ft = fm.beginTransaction()
        if (request.getEventType() is OrigFragmentEvent.OrigEventType.SHOW) {
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
        var result = ""
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

        var navController : NavController? = null
        request.getFragmentManager().findFragmentById(request.getNavHostId())?.let {
            if (it is NavHostFragment) {
                navController = it.navController
            }
        }

        if (navController == null) {
            Log.e(TAG, "navAction: nav controller  is null ")
            return
        }

        val eventType = request.getEventType()
        if (eventType is NavFragmentEvent.NavEventType.NAVIGATE) {
            navController?.navigate(eventType.actionId)
        } else if (eventType is NavFragmentEvent.NavEventType.NAVIGATE_UP) {
            navController?.navigateUp()
        }
    }

    override fun onActivityDestroy(activityName: String) {
        fragmentTreeMap.remove(activityName)
    }

}