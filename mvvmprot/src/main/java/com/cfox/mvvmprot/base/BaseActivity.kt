package com.cfox.mvvmprot.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.eventdata.ActivityEventData
import com.cfox.mvvmprot.base.eventdata.DialogEventData
import com.cfox.mvvmprot.base.eventdata.FragmentEventData
import com.cfox.mvvmprot.base.eventdata.IEventData
import com.cfox.mvvmprot.base.eventstrategy.*
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel<*>> : RxAppCompatActivity(), IBaseView {
    private var binding: V? = null
    private var viewModel: VM? = null
    private var viewModelId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState)

        //私有的ViewModel与View的契约事件回调逻辑
        registerUIEventLiveDataCallBack()
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()

    }

    private fun registerUIEventLiveDataCallBack() {

        viewModel?.let {
            it.getUEvent().getFinishEvent().observe(this, Observer {
                    finish()
                })

            it.getUEvent().getBackPressedEvent().observe(this, Observer {
                onBackPressed()
            })

            it.getUEvent().getActivityEvent().observe(this, Observer { activityEventData ->
                onActivityEvent(activityEventData)
            })

            it.getUEvent().getFragmentEvent().observe(this, Observer { fragmentEventData ->
                onFragmentEvent(fragmentEventData)
            })

            it.getUEvent().getDialogEvent().observe(this, Observer { dialogEventData ->
                onDialogEvent(dialogEventData)
            })

            it.getUEvent().getOtherEvent().observe(this, Observer { uiEventData ->
                onOtherEvent(uiEventData)
            })
        }
    }

    open fun onOtherEvent(eventData: IEventData) {
        val otherStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.OTHER)
        if (otherStrategy is IOtherStrategy) {
            otherStrategy.execute(eventData)
        }
    }

    open fun onDialogEvent(dialogEventData: DialogEventData) {
        val dialogStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.DIALOG)
        if (dialogStrategy is IDialogStrategy) {
            dialogStrategy.execute(dialogEventData)
        }
    }

    open fun onActivityEvent(activityEventData: ActivityEventData) {
        val activityStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.ACTIVITY)
        if (activityStrategy is IActivityStrategy) {
            activityStrategy.execute(activityEventData)
        }
    }

    open fun onFragmentEvent(fragmentEventData: FragmentEventData) {
        val fragmentStrategy = MPort.getConfig()?.getStrategyManager()?.getStrategy(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.execute(fragmentEventData)
        }
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<BaseViewModel<*>>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        }

        binding?.let {
            //关联ViewModel
            it.setVariable(viewModelId, viewModel)
            //支持LiveData绑定xml，数据改变，UI自动会更新
            it.lifecycleOwner = this
        }

        //让ViewModel拥有View的生命周期感应
        viewModel?.let {
            lifecycle.addObserver(it)
        }
        viewModel?.injectLifecycleProvider(this)
    }


    override fun initData() {}

    override fun initParam() {}

    override fun initViewObservable() {}

    open fun initViewModel(): VM? = null

    open fun createViewModeFactory() : ViewModelProvider.Factory? = null

    abstract fun initVariableId(): Int

    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> createViewModel(activity: FragmentActivity?, cls: Class<T>?): T {
        return ViewModelProviders.of(activity!!, createViewModeFactory())[cls!!]
    }
}