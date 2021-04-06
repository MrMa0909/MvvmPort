package com.cfox.mvvmprot.base.strategy.uievent

import android.app.Activity
import androidx.fragment.app.Fragment

abstract class AbsDialogEvent : IUIEvent {
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
}