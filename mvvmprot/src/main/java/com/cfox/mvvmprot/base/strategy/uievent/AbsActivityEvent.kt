package com.cfox.mvvmprot.base.strategy.uievent

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.lang.ref.SoftReference

/**
 * Activity event 事件信息抽象载体，该抽象类的主要作用是启动activity，
 * 可以根据实际需求通过继承AbsActivityEvent类，实现自定义事件，并携带启动activity 中需要的一些参数
 * 在 AbsActivityEvent 类中封装了一些必要的数据，在使用时，不要长期持有，长期持有会有内存泄漏风险
 */
abstract class AbsActivityEvent : IUIEvent {

    private var activity : AppCompatActivity ? = null
    private var fragment : Fragment ? = null

    private var activityReference : SoftReference<AppCompatActivity>? = null
    private var fragmentReference : SoftReference<Fragment>? = null

    /**
     * 设置当前经过的 activity
     */
    internal fun setActivity(activity: AppCompatActivity) {
        this.activityReference = SoftReference(activity)
    }

    /**
     * 设置当前经过的Fragment ，如果不是在 Fragment 中发送的事件，Fragment 为 null
     */
    internal fun setFragment(fragment: Fragment) {
        this.fragmentReference = SoftReference(fragment)
    }

    /**
     * 获取事件传递时经过的activity
     */
    fun getActivity() : AppCompatActivity? {
        return activityReference?.get()
    }

    /**
     * 获取事件经过的Fragment ， 可能为 null
     */
    fun getFragment() : Fragment ? {
        return fragmentReference?.get()
    }
}