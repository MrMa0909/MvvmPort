package com.cfox.mvvmprot.base.strategy.uievent

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class AbsFragmentEvent : IUIEvent {
    private lateinit var fragmentManager : FragmentManager
    private lateinit var activity : Activity

    private var fragment : Fragment? = null

    internal fun setActivity(activity: Activity) {
        this.activity = activity
    }

    internal fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    fun getActivity() : Activity {
        return activity
    }

    fun getFragment() : Fragment? {
        return fragment
    }

    fun getActivityName() : String {
        return activity.javaClass.name
    }

    internal fun setFragmentManager(fragmentManager : FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun getFragmentManager() : FragmentManager {
        return fragmentManager
    }
}