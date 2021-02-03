package com.cfox.mvvmprot.base.strategy.impl.event

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cfox.mvvmprot.base.strategy.uievent.FragmentEvent

class NavFragmentEvent(val navHostId: Int, val actionId: Int) : FragmentEvent() {

    fun getNavController() : NavController? {
        var navController : NavController ? = null
        getFragmentManager().findFragmentById(navHostId)?.let {
            if (it is NavHostFragment) {
                navController = it.navController
            }
        }
        return navController
    }
}