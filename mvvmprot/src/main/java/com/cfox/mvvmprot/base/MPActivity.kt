package com.cfox.mvvmprot.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.cfox.mvvmprot.app.MPort
import com.cfox.mvvmprot.base.strategy.*
import com.cfox.mvvmprot.base.strategy.uievent.*
import com.cfox.mvvmprot.base.viewmodel.MPSViewModel
import com.cfox.mvvmprot.base.viewmodel.MPViewModel
import com.cfox.mvvmprot.base.viewmodel.ViewModelParam
import com.cfox.mvvmprot.base.viewmodel.ViewModelFactory
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class MPActivity<V : ViewDataBinding, VM : MPViewModel<*>, SVM : MPSViewModel> : RxAppCompatActivity(), IBaseView {

    companion object {
        private const val CLS_NAME = "com.cfox.mvvmprot.base.MPActivity"
    }

    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    private lateinit var shareViewModel : SVM
    private var viewModelId: Int = 0

    private var localActivityStrategy : IActivityStrategy<AbsActivityEvent> ? = null
    private var localFragmentStrategy : IFragmentStrategy<AbsFragmentEvent> ? = null
    private var localDialogStrategy : IDialogStrategy<AbsDialogEvent> ? = null
    private var localOtherStrategy : IOtherStrategy<IUIEvent> ? = null

    internal fun getShareModel() : SVM {
        if (::shareViewModel.isInitialized) {
            return shareViewModel
        }
        attachShareViewModel(javaClass)
        return shareViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachShareViewModel(javaClass)
        attachViewModel(javaClass)
        initViewDataBinding(savedInstanceState)
        registerUIEventLiveDataCallBack()
        initData()
        initViewObservable()
    }

    @Synchronized
    private fun attachShareViewModel(cls : Class<*>) {
        if (cls.name == CLS_NAME) {
            return
        }
        initShareViewModel(cls)
        if (!::shareViewModel.isInitialized) {
            attachShareViewModel(cls.superclass)
        }
    }

    private fun attachViewModel(cls : Class<*>) {
        if (cls.name == CLS_NAME) {
            return
        }
        initViewModel(cls)
        if (!::viewModel.isInitialized) {
            attachViewModel(cls.superclass)
        }
    }

    private fun initViewModel(cls : Class<*>) {
        val type = cls.genericSuperclass
        val argsSize = if (type is ParameterizedType) {
            type.actualTypeArguments.size
        } else {
            0
        }
        if (::viewModel.isInitialized || argsSize < 2) {
            return
        }

        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<MPViewModel<*>>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            MPViewModel::class.java
        }
        viewModel = getViewModel(this, modelClass, viewModelFactory) as VM
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        viewModel.injectLifecycleProvider(this)
    }

    private fun initShareViewModel(cls : Class<*>) {
        val type = cls.genericSuperclass
        val argsSize = if (type is ParameterizedType) {
            type.actualTypeArguments.size
        } else {
            0
        }
        if (::viewModel.isInitialized || argsSize < 3) {
            return
        }
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[2] as Class<SVM>
        } else {
            MPSViewModel::class.java
        }
        shareViewModel = getViewModel(this, modelClass, shareViewModelFactory) as SVM
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this

    }

    private val viewModelFactory =
        ViewModelFactory {
            val vmr = createViewModelParam()
            vmr.application = application
            vmr.shareViewMode = getShareModel()
            vmr
        }

    private val shareViewModelFactory =
        ViewModelFactory {
            application
        }

    private fun <T : ViewModel?> getViewModel(activity: FragmentActivity?, cls: Class<T>?, factory: ViewModelFactory<*>): T {
        return ViewModelProviders.of(activity!!, factory)[cls!!]
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
            strategy = getActivityStrategy()
        }

        activityEvent.setActivity(this)
        strategy?.execute(activityEvent)
    }

    internal fun fragmentEvent(fragmentEvent : AbsFragmentEvent, fragmentStrategy : IFragmentStrategy<AbsFragmentEvent> ?) {

        var strategy  = fragmentStrategy
        if (strategy == null) {
            strategy = getFragmentStrategy()
        }

        fragmentEvent.setActivity(this)
        fragmentEvent.setFragmentManager(supportFragmentManager)
        strategy?.execute(fragmentEvent)
    }

    internal fun dialogEvent(dialogEvent: AbsDialogEvent, dialogStrategy: IDialogStrategy<AbsDialogEvent> ?) {
        var strategy  = dialogStrategy
        if (strategy == null) {
            strategy = getDialogStrategy()
        }

        strategy?.execute(dialogEvent)
    }

    internal fun otherEvent(uiEvent : IUIEvent, otherStrategy: IOtherStrategy<IUIEvent>?) {

        var strategy  = otherStrategy
        if (strategy == null) {
            strategy = getOtherStrategy()
        }
        strategy?.execute(uiEvent)
    }

    private fun getActivityStrategy() : IActivityStrategy<AbsActivityEvent> ? {
        if (localActivityStrategy == null) {
            localActivityStrategy = initActivityStrategy()
        }

        if (localActivityStrategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsActivityEvent>(StrategyType.ACTIVITY)
            if (globalStrategy is IActivityStrategy<AbsActivityEvent>) {
                localActivityStrategy = globalStrategy
            }
        }
        return localActivityStrategy
    }

    private fun getFragmentStrategy() : IFragmentStrategy<AbsFragmentEvent> ? {
        if (localFragmentStrategy == null) {
            localFragmentStrategy = initFragmentStrategy()
        }

        if (localFragmentStrategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsFragmentEvent>(StrategyType.FRAGMENT)
            if (globalStrategy is IFragmentStrategy<AbsFragmentEvent>) {
                localFragmentStrategy = globalStrategy
            }
        }
        return localFragmentStrategy
    }

    private fun getDialogStrategy() : IDialogStrategy<AbsDialogEvent> ?{
        if (localDialogStrategy == null) {
            localDialogStrategy = initDialogStrategy()
        }

        if (localDialogStrategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsDialogEvent>(StrategyType.DIALOG)
            if (globalStrategy is IDialogStrategy<AbsDialogEvent> ) {
                localDialogStrategy = globalStrategy
            }
        }

        return localDialogStrategy
    }

    private fun getOtherStrategy() : IOtherStrategy<IUIEvent> ? {
        if (localOtherStrategy == null) {
            localOtherStrategy = initOtherStrategy()
        }

        if (localOtherStrategy == null) {
            val globalStrategy = MPort.getConfig().getStrategyManager().getStrategy<IUIEvent>(StrategyType.OTHER)
            if (globalStrategy is IOtherStrategy<IUIEvent> ) {
                localOtherStrategy = globalStrategy
            }
        }

        return localOtherStrategy
    }

    open fun initOtherStrategy() : IOtherStrategy<IUIEvent> ? = null

    open fun initDialogStrategy() : IDialogStrategy<AbsDialogEvent> ? = null

    open fun initActivityStrategy() : IActivityStrategy<AbsActivityEvent> ? = null

    open fun initFragmentStrategy() : IFragmentStrategy<AbsFragmentEvent> ? = null

    open fun createViewModelParam() : ViewModelParam = ViewModelParam()

    override fun initData() {}

    override fun initViewObservable() {}

    abstract fun initVariableId(): Int

    abstract fun initContentView(savedInstanceState: Bundle?): Int

    override fun onDestroy() {
        super.onDestroy()
        val fragmentStrategy = MPort.getConfig().getStrategyManager().getStrategy<AbsFragmentEvent>(StrategyType.FRAGMENT)
        if (fragmentStrategy is IFragmentStrategy) {
            fragmentStrategy.onActivityDestroy(this.javaClass.simpleName)
        }
    }
}