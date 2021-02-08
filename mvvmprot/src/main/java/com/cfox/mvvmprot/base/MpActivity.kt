package com.cfox.mvvmprot.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.strategy.*
import com.cfox.mvvmprot.base.strategy.impl.event.ActivityEvent
import com.cfox.mvvmprot.base.strategy.uievent.*
import com.cfox.mvvmprot.base.viewmodel.MpViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelRequest
import com.cfox.mvvmprot.base.viewmodel.ViewModelFactory
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class MpActivity<V : ViewDataBinding, VM : MpViewModel<*>> : RxAppCompatActivity(), IBaseView {
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    private var viewModelId: Int = 0

    internal fun getShareModel() : VM {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViewDataBinding(savedInstanceState)
        registerUIEventLiveDataCallBack()
        initData()
        initViewObservable()
    }

    private fun initViewModel() {
        var viewModeTmp = createViewModel()
        if (viewModeTmp == null) {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<MpViewModel<*>>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                MpViewModel::class.java
            }
            viewModeTmp = getViewModel(this, modelClass) as VM
        }
        viewModel = viewModeTmp
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        viewModel.injectLifecycleProvider(this)
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this

    }

    private fun registerUIEventLiveDataCallBack() {

        viewModel.let {
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
    private fun onActivityEvent(activityEvent: AbsActivityEvent) {
        activityEvent(activityEvent, null)
    }

    private fun onFragmentEvent(fragmentEvent : AbsFragmentEvent) {
        fragmentEvent(fragmentEvent, null)
    }

    private fun onDialogEvent(dialogEvent: AbsDialogEvent) {
        dialogEvent(dialogEvent, null)
    }

    private fun onOtherEvent(uiEvent : IUIEvent) {
        otherEvent(uiEvent, null)
    }

    internal fun activityEvent(activityEvent: AbsActivityEvent, activityStrategy : IActivityStrategy<AbsActivityEvent> ?) {
        var strategy : IActivityStrategy<AbsActivityEvent>? = activityStrategy
        if (strategy == null) {
            strategy = buildActivityStrategy()
        }

        if (strategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsActivityEvent>(StrategyType.ACTIVITY)
            if (globalStrategy is IActivityStrategy<AbsActivityEvent>) {
                strategy = globalStrategy
            }
        }
        activityEvent.setContext(this)
        strategy?.execute(activityEvent)
    }

    internal fun fragmentEvent(fragmentEvent : AbsFragmentEvent, fragmentStrategy : IFragmentStrategy<AbsFragmentEvent> ?) {

        var strategy  = fragmentStrategy
        if (strategy == null) {
            strategy = buildFragmentStrategy()
        }

        if (strategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsFragmentEvent>(StrategyType.FRAGMENT)
            if (globalStrategy is IFragmentStrategy<AbsFragmentEvent>) {
                strategy = globalStrategy
            }
        }
        fragmentEvent.setActivityName(this.javaClass.name)
        fragmentEvent.setFragmentManager(supportFragmentManager)
        strategy?.execute(fragmentEvent)
    }

    internal fun dialogEvent(dialogEvent: AbsDialogEvent, dialogStrategy: IDialogStrategy<AbsDialogEvent> ?) {
        var strategy  = dialogStrategy
        if (strategy == null) {
            strategy = buildDialogStrategy()
        }

        if (strategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsDialogEvent>(StrategyType.DIALOG)
            if (globalStrategy is IDialogStrategy<AbsDialogEvent> ) {
                strategy = globalStrategy
            }
        }
        strategy?.execute(dialogEvent)
    }

    internal fun otherEvent(uiEvent : IUIEvent, otherStrategy: IOtherStrategy<IUIEvent>?) {

        var strategy  = otherStrategy
        if (strategy == null) {
            strategy = buildOtherStrategy()
        }

        if (strategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<IUIEvent>(StrategyType.OTHER)
            if (globalStrategy is IOtherStrategy<IUIEvent> ) {
                strategy = globalStrategy
            }
        }
        strategy?.execute(uiEvent)
    }

    open fun buildOtherStrategy() : IOtherStrategy<IUIEvent> ? = null

    open fun buildDialogStrategy() : IDialogStrategy<AbsDialogEvent> ? = null

    open fun buildActivityStrategy() : IActivityStrategy<AbsActivityEvent> ? = null

    open fun buildFragmentStrategy() : IFragmentStrategy<AbsFragmentEvent> ? = null

    override fun initData() {}

    override fun initViewObservable() {}

    open fun createViewModel(): VM? = null

    abstract fun initVariableId(): Int

    abstract fun initContentView(savedInstanceState: Bundle?): Int

    open fun createViewModelRequest() : ViewModelRequest =
        ViewModelRequest()

    private val viewModelFactory =
        ViewModelFactory {
            val vmr = createViewModelRequest()
            vmr.application = application
            vmr
        }

    private fun <T : ViewModel?> getViewModel(activity: FragmentActivity?, cls: Class<T>?): T {
        return ViewModelProviders.of(activity!!, viewModelFactory)[cls!!]
    }

    override fun onDestroy() {
        super.onDestroy()
        val fragmentStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsFragmentEvent>(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.onActivityDestroy(this.javaClass.simpleName)
        }
    }
}