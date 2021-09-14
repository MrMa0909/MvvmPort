package com.cfox.appdemo.ui.main

import android.os.Bundle
import com.cfox.appdemo.BR
import com.cfox.appdemo.R
import com.cfox.appdemo.base.ShareActivity
import com.cfox.appdemo.databinding.ActivityMainBinding
import com.cfox.mvvmprot.base.strategy.IActivityStrategy
import com.cfox.mvvmprot.base.strategy.IDialogStrategy
import com.cfox.mvvmprot.base.strategy.IFragmentStrategy
import com.cfox.mvvmprot.base.strategy.IOtherStrategy
import com.cfox.mvvmprot.base.strategy.uievent.*
import com.cfox.mvvmprot.support.strategy.event.ActivityEvent

class MainActivity : ShareActivity<ActivityMainBinding, MainViewModel, MainShareViewModel>() {

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    /**
     * 自定义单独的打开activity 事件策略
     *
     */
    override fun initActivityStrategy(): IActivityStrategy<AbsActivityEvent>? {
        return object : AbsActivityStrategy<ActivityEvent>() {
            override fun onExecute(event: ActivityEvent) {
                event.buildStartIntent()?.let {
                    event.getActivity()?.startActivity(it)
                }
            }
        }
    }

    /**
     * 自定义打开策略
     */
//    override fun initFragmentStrategy(): IFragmentStrategy<AbsFragmentEvent>? {
//        return super.initFragmentStrategy()
//    }
//
//    override fun initDialogStrategy(): IDialogStrategy<AbsDialogEvent>? {
//        return super.initDialogStrategy()
//    }
//
//    override fun initOtherStrategy(): IOtherStrategy<IUIEvent>? {
//        return super.initOtherStrategy()
//    }
}